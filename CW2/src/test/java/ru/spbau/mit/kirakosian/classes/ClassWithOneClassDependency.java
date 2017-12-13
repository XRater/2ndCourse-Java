package ru.spbau.mit.kirakosian.classes;

@SuppressWarnings("unused")
public class ClassWithOneClassDependency {

    public final ClassWithoutDependencies dependency;

    public ClassWithOneClassDependency(final ClassWithoutDependencies dependency) {
        this.dependency = dependency;
    }
}