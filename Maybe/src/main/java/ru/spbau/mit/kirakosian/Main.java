package ru.spbau.mit.kirakosian;

import org.jetbrains.annotations.NotNull;
import ru.spbau.mit.kirakosian.exceptions.NoElementInMaybeException;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is a console utility to work with files. The utility reads lines from the input file
 * and writes found numbers to the output file (if the line was a number it writes the number, and null otherwise)
 * <p>
 * Maybe class is used for implementation.
 */
public class Main {

    private final static ArrayList<Maybe<Integer>> list = new ArrayList<>();

    /**
     * Main method in the class. For more information see class docs
     *
     * @param args: source dest
     */
    public static void main(@NotNull final String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: <source> <dest>");
            return;
        }
        try {
            readInts(args[0]);
        } catch (@NotNull final FileNotFoundException e) {
            System.out.println("Error. File does not exist or not enough permissions");
        }
        try {
            writeInts(args[1]);
        } catch (@NotNull final IOException e) {
            System.out.println("Errors occurred while writing");
        }
        list.clear();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void writeInts(@NotNull final String output) throws IOException {
        final File out = new File(output);
        out.createNewFile();
        try (final Writer writer = new FileWriter(new File(output))) {
            for (final Maybe<Integer> maybe : list) {
                if (!maybe.isPresent()) {
                    writer.write("null");
                } else {
                    Integer value = 0;
                    try {
                        //noinspection ConstantConditions
                        value = maybe.get() * maybe.get();
                    } catch (final NoElementInMaybeException e) {
                        System.out.println("There was no value in Maybe. Check for" +
                                " the Maybe class version.");
                    }
                    writer.write(value.toString());
                }
                writer.write('\n');
            }
            // No need anymore but there is no guarantee that writer flushes before closing (in docs)
            writer.flush();
        }
    }

    private static void readInts(@NotNull final String input) throws FileNotFoundException {
        try (final Scanner scanner = new Scanner(new File(input))) {
            while (scanner.hasNext()) {
                final String line = scanner.nextLine();
                try {
                    list.add(Maybe.just(Integer.parseInt(line)));
                } catch (@NotNull final NumberFormatException e) {
                    list.add(Maybe.nothing());
                }
            }
        }
    }

}
