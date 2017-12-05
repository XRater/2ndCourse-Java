package mit.spbau.kirakosian.sp;

import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SecondPartTasksTest {

    private final String sourceDir = "./resources";

    @Test
    void testFindQuotesOneFile() throws IOException {
        final File source = new File(sourceDir);
        //noinspection ResultOfMethodCallIgnored
        source.mkdir();

        createFile("file1", "hello", "heLLo2", "hmm hmmm hello?", "", "   ", "wowowhellowew");

        assertThat(SecondPartTasks.findQuotes(List.of(getFileName("file1")), "hello"),
                containsInAnyOrder("hello", "hmm hmmm hello?", "wowowhellowew"));

        deleteFile(sourceDir);
    }

    @Test
    void testFindQuotesManyFile() throws IOException {
        final File source = new File(sourceDir);
        //noinspection ResultOfMethodCallIgnored
        source.mkdir();

        createFile("myFile1", "operator <$>", "operator <*>", "mf <$> mx");
        createFile("myFile2", "mystring", "symbols", "#@#%");
        createFile("myFile3", "mat<*>c<*>h!", "one <*> more");


        assertThat(SecondPartTasks.findQuotes(
                List.of(getFileName("myFile1"), getFileName("myFile2"), getFileName("myFile3")), "<*>"),
                containsInAnyOrder("operator <*>", "mat<*>c<*>h!", "one <*> more"));

        deleteFile(sourceDir);
    }

    @Test
    void testFindQuotesOneEmpty() throws IOException {
        final File source = new File(sourceDir);
        //noinspection ResultOfMethodCallIgnored
        source.mkdir();

        createFile("file1");

        assertThat(SecondPartTasks.findQuotes(List.of(getFileName("file1")), "hello"), is(empty()));

        deleteFile(sourceDir);
    }


    @Test
    void testPiDividedBy4() {
        for (int i = 0; i < 20; i++) {
            assertEquals(SecondPartTasks.piDividedBy4(), Math.PI / 4, 0.2);
        }
    }

    @Test
    void testFindPrinter() {
        final Map<String, List<String>> map = new HashMap<>();
        map.put("Arthur", List.of("1234567890", "", "hellohellohello"));
        map.put("John", List.of("", "", ""));
        map.put("Kate", List.of("123456789012345678901234567890"));

        assertThat(SecondPartTasks.findPrinter(map), is("Kate"));
    }

    @Test
    void testFindPrinterEmpty() {
        final Map<String, List<String>> map = new HashMap<>();
        map.put("Arthur", List.of("", "", ""));
        map.put("John", List.of());
        map.put("Kate", List.of("", ""));

        assertThat(SecondPartTasks.findPrinter(map), is(oneOf("Arthur", "John", "Kate")));
    }

    @Test
    void testFindPrinterNoOne() {
        final Map<String, List<String>> map = new HashMap<>();

        assertEquals(SecondPartTasks.findPrinter(map), null);
    }

    @Test
    void testCalculateGlobalOrder() {
        final Map<String, Integer> order1 = new HashMap<>();
        order1.put("apple", 10);
        order1.put("orange", 0);
        order1.put("pear", 2);

        final Map<String, Integer> order2 = new HashMap<>();
        order2.put("tomato", 0);

        final Map<String, Integer> order3 = new HashMap<>();
        order3.put("tomato", 15);
        order3.put("apple", 10);
        order3.put("pear", -2);

        final Map<String, Integer> expected = ImmutableMap.of(
                "apple", 20, "orange", 0, "pear", 0, "tomato", 15);
        assertEquals(expected, SecondPartTasks.calculateGlobalOrder(
                List.of(order1, order2, order3)));
    }

    @Test
    void testCalculateGlobalOrderEmpty() {
        final Map<String, Integer> expected = ImmutableMap.of();
        assertEquals(expected, SecondPartTasks.calculateGlobalOrder(
                List.of()));
    }


    private String getFileName(final String name) {
        return sourceDir + File.separator + name;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createFile(final String name, final String... lines) throws IOException {
        final File file = new File(getFileName(name));
        new File(file.getParent()).mkdirs();
        file.createNewFile();

        try (final PrintWriter writer = new PrintWriter(file)) {
            for (final String line : lines) {
                writer.append(line).append('\n');
            }
        }
    }

    private void deleteFile(@NotNull final String name) {
        final File file = new File(name);
        if (!file.isDirectory()) {
            assertTrue(file.delete());
        } else {
            //noinspection ConstantConditions
            for (final File inner : file.listFiles()) {
                deleteFile(inner.getAbsolutePath());
            }
            assertTrue(file.delete());
        }
    }
}