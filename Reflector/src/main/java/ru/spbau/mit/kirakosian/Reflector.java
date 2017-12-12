package ru.spbau.mit.kirakosian;

import java.io.File;
import java.io.IOException;
import java.io.ObjectStreamClass;
import java.io.PrintWriter;
import java.lang.reflect.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Reflector {

    private static final Set<String> imports = new HashSet<>();
    private static String packageName;

    public static void printStructure(final Class<?> someClass) throws IOException {
        imports.clear();

        final File file = Utils.createFile("someClass.java");
        final File proxy = Utils.createTemporaryFile("proxy");
        try (final PrintWriter writer = new PrintWriter(proxy)) {
            printClass(someClass, writer);
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

    private static void printClass(final Class<?> someClass, final PrintWriter writer) {
        packageName = someClass.getPackageName();

        printClassName(someClass, writer);
        writer.append('\n');

        for (final Field field : someClass.getDeclaredFields()) {
            printField(field, writer);
            writer.append('\n');
        }

        for (final Method method : someClass.getDeclaredMethods()) {
            printMethod(method, writer);
            writer.append('\n');
        }
        writer.append("}");
    }

    private static void printClassName(final Class<?> someClass, final PrintWriter writer) {
        final String name = someClass.getSimpleName();

        final int modifiers = someClass.getModifiers();
        final String modifiersNames = Modifier.toString(modifiers);

        final String extendedType = getStringType(someClass.getSuperclass());

        final AnnotatedType[] interfaces = someClass.getAnnotatedInterfaces();

        writer.append(modifiersNames).append(" class ").append(name);

        if (!extendedType.equals("Object")) {
            writer.append(" extends ").append(extendedType);
        }

        if (interfaces.length != 0) {
            writer.append(" implements");
            String typeName = interfaces[0].getType().getTypeName();
            typeName = simplifyName(typeName);
            writer.append(" ").append(typeName);

            for (int i = 1; i < interfaces.length; i++) {
                typeName = interfaces[i].getType().getTypeName();
                typeName = simplifyName(typeName);
                writer.append(", ").append(typeName);
            }
        }

        writer.append(" {\n");
    }

    private static void printField(final Field field, final PrintWriter writer) {
        final String filedName = field.getName();
        final String filedTypeName = getStringType(field.getType());

        final int modifiers = field.getModifiers();
        final String modifiersName = Modifier.toString(modifiers);

        writer.append('\t').append(modifiersName).append(' ').append(filedTypeName)
                .append(' ').append(filedName);

        if (Modifier.isFinal(modifiers)) {
            writer.append(" = ");
            writer.append(getInstance(field.getType()));
        }

        writer.append(";\n");
    }

    private static void printMethod(final Method method, final PrintWriter writer) {
        final String name = method.getName();

        final int modifiers = method.getModifiers();
        final String modifiersNames = Modifier.toString(modifiers);
        final Class<?> returnType = method.getReturnType();
        final Parameter[] parameters = method.getParameters();

        writer.append('\t').append(modifiersNames);
        if (modifiers != 0) {
            writer.append(' ');
        }
        writer.append(getStringType(returnType)).append(' ').append(name).append("(");
        if (parameters.length != 0) {
            printParameter(parameters[0], writer);
            for (int i = 1; i < parameters.length; i++) {
                writer.append(", ");
                printParameter(parameters[i], writer);
            }
        }
        writer.append(")");

        if (Modifier.isNative(modifiers)) {
            writer.append(";\n");
            return;
        }

        writer.append(" {\n");
        if (!getStringType(returnType).equals("void")) {
            writer.append("\t\t").append("return ");
            writer.append(getInstance(returnType));
            writer.append(";\n");
        }
        writer.append("\t}\n");
    }

    private static String getInstance(final Class<?> returnType) {
        if (!returnType.isPrimitive()) {
            return "null";
        } else if (returnType.equals(char.class)) {
            return "'0'";
        } else {
            final Object o = Array.get(Array.newInstance(returnType, 1), 0);
            return o.toString();
        }
    }

    private static void printParameter(final Parameter parameter, final PrintWriter writer) {
        final String typeName = getStringType(parameter.getType());
        writer.append(typeName).append(' ').append(parameter.getName());
    }

    private static String getStringType(final Class<?> type) {
        if (type.isArray()) {
            final Class<?> componentType = type.getComponentType();
            if (!componentType.isPrimitive()) {
                simplifyName(componentType.getName());
            }
            return type.getComponentType().getSimpleName() + "[]";
        } else {
            if (!type.isPrimitive()) {
                simplifyName(type.getName());
            }
            return type.getSimpleName();
        }
    }

    // TODO name collisions
    private static String simplifyName(String name) {
        final int firstAngle = name.indexOf("<");
        if (firstAngle != -1) {
            name = name.substring(0, name.indexOf("<"));
        }
        final int index = name.lastIndexOf('.');
        final String pack = name.substring(0, index);
        final String type = name.substring(index + 1);
        // if no name collision add package return type
        if (true) {
            if (!pack.equals(packageName)) {
                imports.add(name);
            }
            return type;
        } else {
            // else replace package if java.lang
            return name;
        }
    }

    public static void main(final String[] args) throws IOException {
        printStructure(Reflector.class);
    }

}
