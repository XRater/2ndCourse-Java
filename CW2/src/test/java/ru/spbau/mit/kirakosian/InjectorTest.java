package ru.spbau.mit.kirakosian;

import org.junit.jupiter.api.Test;
import ru.spbau.mit.kirakosian.classes.*;
import ru.spbau.mit.kirakosian.exceptions.ImplementationNotFoundException;
import ru.spbau.mit.kirakosian.exceptions.InjectionCycleException;
import ru.spbau.mit.kirakosian.exceptions.InjectorException;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;


class InjectorTest {

    @Test
    void injectorShouldInitializeClassWithoutDependencies()
            throws Exception {
        final Object object = Injector.initialize("ru.spbau.mit.kirakosian.classes.ClassWithoutDependencies", Collections.emptyList());
        assertTrue(object instanceof ClassWithoutDependencies);
    }

    @Test
    void injectorShouldInitializeClassWithOneClassDependency()
            throws Exception {
        final Object object = Injector.initialize(
                "ru.spbau.mit.kirakosian.classes.ClassWithOneClassDependency",
                Collections.singletonList("ru.spbau.mit.kirakosian.classes.ClassWithoutDependencies")
        );
        assertTrue(object instanceof ClassWithOneClassDependency);
        final ClassWithOneClassDependency instance = (ClassWithOneClassDependency) object;
        assertTrue(instance.dependency != null);
    }

    @Test
    void injectorShouldInitializeClassWithOneInterfaceDependency()
            throws Exception {
        final Object object = Injector.initialize(
                "ru.spbau.mit.kirakosian.classes.ClassWithOneInterfaceDependency",
                Collections.singletonList("ru.spbau.mit.kirakosian.classes.InterfaceImpl")
        );
        assertTrue(object instanceof ClassWithOneInterfaceDependency);
        final ClassWithOneInterfaceDependency instance = (ClassWithOneInterfaceDependency) object;
        assertTrue(instance.dependency instanceof InterfaceImpl);
    }

    @Test
    void testNoImp() {
        assertThrows(ImplementationNotFoundException.class, () -> Injector.initialize(
                "ru.spbau.mit.kirakosian.classes.ClassWithOneInterfaceDependency",
                Collections.emptyList()
        ));

        assertThrows(ImplementationNotFoundException.class, () -> Injector.initialize(
                "ru.spbau.mit.kirakosian.classes.ClassWithOneClassDependency",
                Collections.emptyList()
        ));
    }

    @Test
    void testCreateOnce() throws InjectorException, ClassNotFoundException {
        InnerClassCreateOnce.cnt = 0;
        final Object object = Injector.initialize(
                "ru.spbau.mit.kirakosian.classes.OuterClass",
                Collections.singletonList("ru.spbau.mit.kirakosian.classes.InnerClassCreateOnce")
        );
        assertTrue(object instanceof OuterClass);
        assertThat(InnerClassCreateOnce.cnt, is(1));
    }

    @Test
    void testCycleDependencies() {
        assertThrows(InjectionCycleException.class, () -> Injector.initialize(
                "ru.spbau.mit.kirakosian.classes.CycleFirst",
                Arrays.asList("ru.spbau.mit.kirakosian.classes.CycleSecond",
                        "ru.spbau.mit.kirakosian.classes.CycleFirst")
        ));

        assertThrows(InjectionCycleException.class, () -> Injector.initialize(
                "ru.spbau.mit.kirakosian.classes.CycleSecond",
                Arrays.asList("ru.spbau.mit.kirakosian.classes.CycleFirst",
                        "ru.spbau.mit.kirakosian.classes.CycleSecond")
        ));
    }

    @Test
    void testAbstractDependencies() {
        assertThrows(ImplementationNotFoundException.class, () -> Injector.initialize(
                "ru.spbau.mit.kirakosian.classes.DependencyFromAbstract",
                Collections.singletonList("ru.spbau.mit.kirakosian.classes.AbstractClass")
        ));
    }

    @Test
    void testMoreDependencies() throws InjectorException, ClassNotFoundException {
        B.cnt = 0;
        C.cnt = 0;
        D.cnt = 0;
        final Object object = Injector.initialize(
                "ru.spbau.mit.kirakosian.classes.A",
                Arrays.asList("ru.spbau.mit.kirakosian.classes.B",
                        "ru.spbau.mit.kirakosian.classes.C",
                        "ru.spbau.mit.kirakosian.classes.D")
        );
        assertTrue(object instanceof A);
        assertThat(B.cnt, is(1));
        assertThat(C.cnt, is(1));
        assertThat(D.cnt, is(1));
    }
}