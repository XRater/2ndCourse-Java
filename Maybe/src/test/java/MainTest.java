import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("SameParameterValue")
public class MainTest {

    @Test
    public void testMain() throws IOException {
        testInput(new String[]{},
                new String[]{});
        testInput(new String[]{"123", "null", "4"},
                new String[]{"123", "null", "4"});
        testInput(new String[]{"1"},
                new String[]{"1"});
        testInput(new String[]{"h2i", "13null", "there"},
                new String[]{"null", "null", "null"});
    }

    private void testInput(@NotNull final String[] data, @NotNull final String[] result) throws IOException {
        createFile("input.txt", data);
        Main.main(new String[]{"input.txt", "output.txt"});
        checkFile("output.txt", result);
        deleteFiles(new String[]{"input.txt", "output.txt"});
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createFile(@NotNull final String name, @NotNull final String[] lines) throws IOException {
        final File file = new File(name);
        file.createNewFile();
        final Writer writer = new FileWriter(name);
        for (final String line : lines) {
            writer.write(line);
            writer.write('\n');
        }
        writer.flush();
    }

    private void checkFile(@NotNull final String name, @NotNull final String[] data) throws FileNotFoundException {
        final Scanner scanner = new Scanner(new File(name));
        for (final String line : data) {
            assertTrue(scanner.hasNext());
            assertEquals(line, scanner.nextLine());
        }
    }

    private void deleteFiles(@NotNull final String[] names) {
        for (final String name : names) {
            //noinspection ResultOfMethodCallIgnored
            new File(name).delete();
        }
    }


}