import exceptions.NoElementInMaybeException;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private final static ArrayList<Maybe<Integer>> list = new ArrayList<>();

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
        final Writer writer = new FileWriter(new File(output));
        for (final Maybe<Integer> maybe : list) {
            try {
                if (!maybe.isPresent()) {
                    writer.write("null");
                } else {
                    //noinspection ConstantConditions
                    final Integer value = maybe.get() * maybe.get();
                    writer.write(value.toString());
                }
                writer.write('\n');
            } catch (@NotNull final NoElementInMaybeException e) {
                System.out.println("There was no value in Maybe. Check for the Maybe class version.");
            }
        }
        writer.flush();
    }

    private static void readInts(@NotNull final String input) throws FileNotFoundException {
        final Scanner scanner = new Scanner(new File(input));
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
