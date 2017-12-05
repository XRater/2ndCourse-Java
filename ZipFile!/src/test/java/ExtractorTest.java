import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for Extractor class.
 */
@SuppressWarnings("LocalCanBeFinal")
public class ExtractorTest {

    private final String sourceDir = "./source";
    private final String destDir = "./dest";
    private final Random r = new Random();

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void setUp() {
        File source = new File(sourceDir);
        source.mkdir();
        File dest = new File(destDir);
        dest.mkdir();
    }

    private void tearDown() {
        deleteFile(sourceDir);
        deleteFile(destDir);
    }

    @Test
    public void testExtractFilesEmptyDirectory() throws IOException {
        setUp();

        Extractor extractor = new Extractor();
        extractor.extractFiles(sourceDir, ".*", destDir);

        Extractor.Statistic stats = extractor.getStats();
        assertEquals("[]", stats.extractedFiles().toString());
        assertEquals(0, stats.failedExtractions());
        assertEquals(0, stats.errorsNumber());

        extractor.resetStats();
        assertEquals("[]", stats.extractedFiles().toString());
        assertEquals(0, stats.failedExtractions());
        assertEquals(0, stats.errorsNumber());

        tearDown();
    }

    @Test
    public void testExtractFilesNoZips() throws IOException {
        setUp();

        createFile("zip.zip/file");
        createFile("fold/inner");
        createFile("file.txt");

        Extractor extractor = new Extractor();
        extractor.extractFiles(sourceDir, ".*", destDir);

        Extractor.Statistic stats = extractor.getStats();
        assertEquals("[]", stats.extractedFiles().toString());
        assertEquals(0, stats.failedExtractions());
        assertEquals(0, stats.errorsNumber());

        extractor.resetStats();
        assertEquals("[]", stats.extractedFiles().toString());
        assertEquals(0, stats.failedExtractions());
        assertEquals(0, stats.errorsNumber());

        tearDown();
    }

    @Test
    public void testExtractFilesNoInnerFolders() throws IOException {
        testOneZip(new String[]{"file", "file2"},
                new String[]{}, "zip.zip", "t.*");
        testOneZip(new String[]{"file", "file2"},
                new String[]{"file"}, "zip", "....");
        testOneZip(new String[]{"file", "file2"},
                new String[]{"file", "file2"}, "MyZip.txt", "file2?");
        testOneZip(new String[]{"file", "file2.1", "word.txt"},
                new String[]{"file2.1", "word.txt"}, "MyZip.txt", ".*\\..*");
    }

    @Test
    public void testExtractFilesWithInnerFolders() throws IOException {
        testOneZip(new String[]{"folder/"},
                new String[]{}, "zip.zip", ".*");
        testOneZip(new String[]{"folder/file", "folder/file2"},
                new String[]{"folder/file", "folder/file2"},
                "zip.zip", "file.*");
        testOneZip(new String[]{"folder/"},
                new String[]{}, "zip.zip", ".*");
        testOneZip(new String[]{"folder/", "file"},
                new String[]{"file"}, "zip.zip", "f.*");
        testOneZip(new String[]{"folder/inner/file.txt", "af", "folder/inner2/", "f/", "file2"},
                new String[]{"folder/inner/file.txt", "file2"}, "zip.zip", "f.*");
    }

    @Test
    public void testExtractFilesMoreZips() throws IOException {
        setUp();

        createZip(new String[]{"one", "two", "three"}, "zip1.zip");
        createZip(new String[]{"one.1", "two.1", "three.1"}, "zip2.zip");

        Extractor extractor = new Extractor();
        extractor.extractFiles(sourceDir, "t.*", destDir);
        Extractor.Statistic stats = extractor.getStats();

        assertThat(Arrays.asList("two.1", "three.1", "two", "three"),
                containsInAnyOrder(stats.extractedFiles().toArray()));

        tearDown();
    }

    private void testOneZip(@NotNull String[] input, String[] output, String zipName, @NotNull String regex) throws
            IOException {
        setUp();

        Extractor extractor = new Extractor(64);
        createZip(input, zipName);
        extractor.extractFiles(sourceDir, regex, destDir);

        Extractor.Statistic stats = extractor.getStats();
        System.err.println(Arrays.asList(output));
        System.err.println(Arrays.toString(stats.extractedFiles().toArray()));
        assertThat(Arrays.asList(output),
                containsInAnyOrder(stats.extractedFiles().toArray()));
        extractor.resetStats();

        for (String name : stats.extractedFiles()) {
            File file = new File(destDir + File.separator + name);
            assertTrue(file.exists());
        }

        assertEquals("[]", stats.extractedFiles().toString());
        assertEquals(0, stats.failedExtractions());
        assertEquals(0, stats.errorsNumber());

        tearDown();
    }

    private void createZip(@NotNull String[] files, String name) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(
                new FileOutputStream(sourceDir + File.separator + name))) {
            for (String file : files) {
                zos.putNextEntry(new ZipEntry(file));
                byte[] bytes = new byte[r.nextInt(1000)];
                r.nextBytes(bytes);
                zos.write(bytes);
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createFile(String name) throws IOException {
        File file = new File(sourceDir + File.separator + name);
        new File(file.getParent()).mkdirs();
        file.createNewFile();
    }

    private void deleteFile(@NotNull String name) {
        File file = new File(name);
        if (!file.isDirectory()) {
            assertTrue(file.delete());
        } else {
            //noinspection ConstantConditions
            for (File inner : file.listFiles()) {
                deleteFile(inner.getAbsolutePath());
            }
            assertTrue(file.delete());
        }
    }
}