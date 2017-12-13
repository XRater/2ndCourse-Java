package ru.spbau.mit.kirakosian;

import org.jetbrains.annotations.NotNull;
import ru.spbau.mit.kirakosian.exceptions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;

@SuppressWarnings("WeakerAccess")
public class Injector {

    private static Map<Class<?>, Boolean> classes;
    private static Map<Class<?>, Object> implementations;

    /**
     * This method recursively constructs required class using dependencies.
     * <p>
     * The tree must be linear, that means that for any required implementation there must be only
     * one instance for it in class dependencies. Otherwise exception will b thrown.
     * <p>
     * Any class may ave only one constructor. Otherwise exception will be thrown.
     *
     *
     * @param rootClassName name of class to construct
     * @param c collection of string names representing dependencies
     * @return new instance of required class
     * @throws ClassNotFoundException if initial class or one of dependencies class was not found
     * @throws InjectorException if any problem occurred during initialization
     */
    @NotNull
    public static Object initialize(final String rootClassName, @NotNull final Collection<String> c) throws ClassNotFoundException, InjectorException {
        classes = new HashMap<>();
        implementations = new HashMap<>();

        classes.put(Class.forName(rootClassName), false);

        for (final String name : c) {
            classes.put(Class.forName(name), false);
        }

        return build(findClassByName(rootClassName));
    }

    /**
     *
     * @param rootClassName class name to find
     * @return Class object for rootClassName.
     */
    @NotNull
    private static Class<?> findClassByName(final String rootClassName) throws InjectorException {
        Class<?> returnClazz = null;
        for (final Class<?> clazz : classes.keySet()) {
            if (clazz.getName().equals(rootClassName)) {
                if (returnClazz != null) {
                    throw new AmbiguousImplementationException();
                }
                returnClazz = clazz;
            }
        }
        if (returnClazz != null) {
            return returnClazz;
        }
        throw new ClassNotInCollectionException(); // If class was not found in collection.
    }

    @NotNull
    private static Class<?> findClassImp(@NotNull final Class<?> arg) throws InjectorException {
        Class<?> returnClazz = null;

        for (final Class<?> clazz : classes.keySet()) {
            final int modifier = clazz.getModifiers();
            if (arg.isAssignableFrom(clazz) &&
                    !Modifier.isInterface(modifier) && !Modifier.isAbstract(modifier)) {
                if (returnClazz != null) {
                    throw new AmbiguousImplementationException();
                }
                returnClazz = clazz;
            }
        }
        if (returnClazz != null) {
            return returnClazz;
        }
        throw new ImplementationNotFoundException(); // If class was not found in collection.
    }

    @NotNull
    private static Object build(@NotNull final Class<?> clazz) throws InjectorException {
        if (classes.get(clazz)) {
            throw new InjectionCycleException();
        }
        classes.put(clazz, true);
        if (clazz.getConstructors().length > 1) {
            throw new MoreThenOneConstructorForClassException();
        }
        //noinspection LoopStatementThatDoesntLoop
        for (final Constructor<?> constructor : clazz.getConstructors()) {
            final Class<?>[] args = constructor.getParameterTypes();
            final List<Object> argsImps = new ArrayList<>();
            for (final Class<?> arg : args) {
                if (implementations.containsKey(arg)) {
                    argsImps.add(implementations.get(arg));
                } else {
                    argsImps.add(build(findClassImp(arg)));
                }
            }
            final Object classImp;
            try {
                classImp = constructor.newInstance(argsImps.toArray());
            } catch (@NotNull InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new ClassCreationException();
            }
            implementations.put(clazz, classImp);
            return classImp;
        }
        throw new ConstructorNotFoundException(); // If some of classes does not
    }

}
