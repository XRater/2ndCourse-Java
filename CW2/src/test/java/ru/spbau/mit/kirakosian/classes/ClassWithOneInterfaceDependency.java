package ru.spbau.mit.kirakosian.classes;


@SuppressWarnings("unused")
public class ClassWithOneInterfaceDependency {
    public final Interface dependency;

    public ClassWithOneInterfaceDependency(final Interface dependency) {
        this.dependency = dependency;
    }
}