package ru.spbau.mit.kirakosian;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.*;
import java.util.*;

public class Reflector {

    // Packages to import
    private static final Set<String> imports = new HashSet<>();
    // scope classes names
    private static final Set<String> classesNames = new HashSet<>();

    private static String packageName;

    /**
     * The method prints representation of the given with Class object class.
     * <p>
     * File with the printed class may be compiled, but all methods do nothing.
     *
     * @param someClass class to print
     * @throws IOException if any IO exception occurred.
     */
    public static void printStructure(final Class<?> someClass) throws IOException {
        imports.clear();
        classesNames.clear();

        final File file = Utils.createFile(someClass.getSimpleName() + ".java");
        final File proxy = Utils.createTemporaryFile("proxy");
        try (final PrintWriter writer = new PrintWriter(proxy)) {
            printClass(someClass, writer, 0);
        }

        try (final PrintWriter writer = new PrintWriter(file)) {
            writer.append("package ").append(packageName).append(";\n\n");

            for (final String packageToImport : imports) {
                writer.append("import ").append(packageToImport).append(";\n");
            }

            writer.append("\n");
            try (final Scanner scanner = new Scanner(proxy)) {
                while (scanner.hasNext()) {
                    writer.append(scanner.nextLine()).append('\n');
                }
            }
        }
    }

    // The method prints class with the given shift (may be inner class)
    private static void printClass(final Class<?> someClass, final PrintWriter writer, final int shift) {
        classesNames.add(someClass.getSimpleName());

        packageName = someClass.getPackage().getName();

        printClassName(someClass, writer, shift);
        writer.append('\n');

        for (final Field field : someClass.getDeclaredFields()) {
            printField(field, writer, shift);
            writer.append('\n');
        }

        for (final Method method : someClass.getDeclaredMethods()) {
            printMethod(method, writer, shift);
            writer.append('\n');
        }

        for (final Class<?> clazz : someClass.getClasses()) {
            printClass(clazz, writer, shift + 1);
            writer.append('\n');
        }

        printsShift(writer, shift);
        writer.append("}");
    }

    // The method prints class name, modifiers, super class and implemented interfaces. (first string of class)
    private static void printClassName(final Class<?> someClass, final PrintWriter writer, final int shift) {
        final String name = someClass.getSimpleName();

        final int modifiers = someClass.getModifiers();

        final Type extendedType = someClass.getGenericSuperclass();
        final Type[] interfaces = someClass.getGenericInterfaces();

        printsShift(writer, shift);
        writer.append(Modifier.toString(modifiers));
        writer.append(" class ").append(name);
        writer.append(TypeGetter.getGenericVariables(someClass.getTypeParameters()));

        // superclass
        if (!extendedType.getTypeName().equals("java.lang.Object")) {
            writer.append(" extends ").append(TypeGetter.getStringType(extendedType));
        }

        // interfaces
        if (interfaces.length != 0) {
            writer.append(" implements");
            final StringJoiner joiner = new StringJoiner(", ");

            for (final Type type : interfaces) {
                joiner.add(TypeGetter.getStringTypeInner(type));
            }
            writer.append(joiner.toString());
        }

        writer.append(" {\n");
    }

    // The method prints filed of class with the given class shift
    private static void printField(final Field field, final PrintWriter writer, final int shift) {
        final String filedName = field.getName();
        final String filedTypeName = TypeGetter.getStringType(field.getGenericType());

        final int modifiers = field.getModifiers();
        final String modifiersName = Modifier.toString(modifiers);

        printsShift(writer, shift + 1);
        writer.append(modifiersName);
        if (!modifiersName.equals("")) {
            writer.append(' ');
        }
        writer.append(filedTypeName)
                .append(' ').append(filedName);

        if (Modifier.isFinal(modifiers)) {
            writer.append(" = ");
            writer.append(TypeGetter.getInstance(field.getType()));
        }

        writer.append(";\n");
    }

    // The method prints method of class with the given class shift
    private static void printMethod(final Method method, final PrintWriter writer, final int shift) {
        final String name = method.getName();

        final int modifiers = method.getModifiers();
        final String modifiersNames = Modifier.toString(modifiers);
        final Type returnType = method.getGenericReturnType();
        final Type[] parameters = method.getGenericParameterTypes();

        printsShift(writer, shift + 1);
        writer.append(modifiersNames);
        if (!modifiersNames.equals("")) {
            writer.append(' ');
        }
        writer.append(TypeGetter.getStringType(returnType)).append(' ').append(name);
        writer.append(TypeGetter.getGenericVariables(method.getTypeParameters()));
        writer.append("(");
        if (parameters.length != 0) {
            printParameter(parameters[0], 0, writer);
            for (int i = 1; i < parameters.length; i++) {
                writer.append(", ");
                printParameter(parameters[i], i, writer);
            }
        }
        writer.append(")");

        if (Modifier.isNative(modifiers) || Modifier.isAbstract(modifiers)) {
            writer.append(";\n");
            return;
        }

        writer.append(" {\n");
        if (!TypeGetter.getStringType(returnType).equals("void")) {
            printsShift(writer, shift + 2);
            writer.append("return ");
            writer.append(TypeGetter.getInstance(returnType));
            writer.append(";\n");
        }
        printsShift(writer, shift + 1);
        writer.append("}\n");
    }

    // The method prints class shift to the writer
    private static void printsShift(final PrintWriter writer, final int shift) {
        for (int i = 0; i < shift; i++) {
            writer.append('\t');
        }
    }

    // The method adds parameter type to the writer
    private static void printParameter(final Type parameter, final int paramNumber, final PrintWriter writer) {
        final String typeName = TypeGetter.getStringType(parameter);
        writer.append(typeName).append(' ').append("var").append(String.valueOf(paramNumber));
    }

    // Takes class name (primitive type is not expected)
    private static String simplifyName(String name) {
        final int firstAngle = name.indexOf("<");
        if (firstAngle != -1) {
            name = name.substring(0, name.indexOf("<"));
        }
        final int index = name.lastIndexOf('.');
        if (index == -1) {
            return name;
        }
        final String pack = name.substring(0, index);
        final String type = name.substring(index + 1);
        // if no name collision add package and return type
        if (!classesNames.contains(type)) {
            classesNames.add(type);
            if (!pack.equals(packageName)) {
                imports.add(name);
            }
            return type;
        } else {
            if (imports.contains(name) || pack.equals(packageName)) {
                return type;
            }
            return name;
        }
    }

    public static void main(final String[] args) throws IOException {
    }


    @SuppressWarnings("WeakerAccess")
    public static class TypeGetter {

        private final static Set<Type> types = new HashSet<>();

        public static String getStringType(final Type type) {
            types.clear();
            return getStringTypeInner(type);
        }

        public static String getGenericVariables(final TypeVariable<?>[] typeParameters) {
            final StringJoiner joiner = new StringJoiner(", ", "<", ">");

            for (final TypeVariable tVar : typeParameters) {
                joiner.add(getStringTypeInner(tVar));
            }

            return joiner.toString().equals("<>") ? "" : joiner.toString();
        }

        // returns string representation of the given type
        private static String getStringTypeInner(final Type type) {
            final StringBuilder sb = new StringBuilder();
            if (type instanceof ParameterizedType) {
                final ParameterizedType pType = (ParameterizedType) type;
                sb.append(getClassName((Class<?>) pType.getRawType()));

                final StringJoiner joiner = new StringJoiner(", ", "<", ">");
                for (final Type arg : pType.getActualTypeArguments()) {
                    joiner.add(getStringTypeInner(arg));
                }
                sb.append(joiner.toString());

            } else if (type instanceof WildcardType) {
                final WildcardType wType = (WildcardType) type;
                sb.append("?");

                // Lower bounds
                if (wType.getLowerBounds().length != 0) {
                    final StringJoiner lowerJoiner = new StringJoiner(" & ", " super ", "");
                    for (final Type t : wType.getLowerBounds()) {
                        lowerJoiner.add(getStringTypeInner(t));
                    }
                    sb.append(lowerJoiner.toString());
                }
                // Upper bounds
                int upperBounds = 0;
                final StringJoiner upperJoiner = new StringJoiner(" & ", " extends ", "");
                for (final Type bound : wType.getUpperBounds()) {
                    if (bound.getTypeName().equals("java.lang.Object")) {
                        continue;
                    }
                    upperJoiner.add(getStringTypeInner(bound));
                    upperBounds++;
                }
                if (upperBounds != 0) {
                    sb.append(upperJoiner.toString());
                }

            } else if (type instanceof TypeVariable<?>) {
                final TypeVariable<?> vType = (TypeVariable<?>) type;
                sb.append(vType.getName());
                if (!types.contains(vType)) {
                    types.add(type);
                    int bounds = 0;
                    final StringJoiner joiner = new StringJoiner(" & ", " extends ", "");
                    for (final Type t : vType.getBounds()) {
                        if (t.getTypeName().equals("java.lang.Object")) {
                            continue;
                        }
                        joiner.add(getStringTypeInner(t));
                        bounds++;
                    }
                    if (bounds != 0) {
                        sb.append(joiner.toString());
                    }
                    types.remove(type);
                }
            } else if (type instanceof GenericArrayType) {
                final GenericArrayType arrayType = (GenericArrayType) type;
                sb.append(getStringType(arrayType.getGenericComponentType()));
                sb.append("[]");
            } else if (type instanceof Class) {
                sb.append(getClassName((Class<?>) type));
            } else {
                throw new RuntimeException(); // should never happen
            }
            return sb.toString();
        }

        private static String getClassName(final Class<?> clazz) {
            if (clazz.isArray()) {
                final Class<?> componentType = clazz.getComponentType();
                if (!componentType.isPrimitive()) {
                    return simplifyName(componentType.getCanonicalName());
                }
                return clazz.getComponentType().getSimpleName() + "[]";
            } else {
                if (!clazz.isPrimitive()) {
                    return simplifyName(clazz.getCanonicalName());
                }
                return clazz.getSimpleName();
            }
        }

        // The method returns instance of class (used for return and final statements).
        private static String getInstance(final Type returnType) {
            final Class<?> clazz;
            if (returnType instanceof Class<?>) {
                clazz = (Class<?>) returnType;
            } else if (returnType instanceof ParameterizedType) {
                clazz = (Class<?>) ((ParameterizedType) returnType).getRawType();
            } else {
                clazz = Object.class;
            }

            if (!clazz.isPrimitive()) {
                return "null";
            }
            if (clazz.equals(boolean.class)) {
                return "false";
            }
            return "0";
        }

    }
}
