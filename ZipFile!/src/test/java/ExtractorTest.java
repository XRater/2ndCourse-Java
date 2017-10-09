import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExtractorTest {


    private String sourceDir = "./sorce";
    private String destDir = "./dest";

    public void setUp() {
        File source = new File(sourceDir);
        assertTrue(source.mkdir());
        File dest = new File(destDir);
        assertTrue(dest.mkdir());
    }

    public void tearDown() {
        deleteFile(sourceDir);
        deleteFile(destDir);
    }

    @Test
    public void testExtractFilesEmptyDirectory() throws IOException {
        setUp();

        Extractor extractor = new Extractor();
        extractor.extractFiles(sourceDir, ".*", destDir);

        Extractor.Statistic stats = extractor.getStats();
        assertEquals(stats.extractedFiles().toString(), "[]");
        assertEquals(stats.failedExtractions(), 0);
        assertEquals(stats.errorsNumber(), 0);

        extractor.resetStats();
        assertEquals(stats.extractedFiles().toString(), "[]");
        assertEquals(stats.failedExtractions(), 0);
        assertEquals(stats.errorsNumber(), 0);

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
        assertEquals(stats.extractedFiles().toString(), "[]");
        assertEquals(stats.failedExtractions(), 0);
        assertEquals(stats.errorsNumber(), 0);

        extractor.resetStats();
        assertEquals(stats.extractedFiles().toString(), "[]");
        assertEquals(stats.failedExtractions(), 0);
        assertEquals(stats.errorsNumber(), 0);

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
    }

    private void testOneZip(String[] input, String[] output, String zipName, String regex) throws IOException {
        setUp();

        Extractor extractor = new Extractor();
        createZip(input, zipName);
        extractor.extractFiles(sourceDir, regex, destDir);

        Extractor.Statistic stats = extractor.getStats();
        assertEquals(Arrays.toString(output), stats.extractedFiles().toString());
        extractor.resetStats();

        assertEquals(stats.extractedFiles().toString(), "[]");
        assertEquals(stats.failedExtractions(), 0);
        assertEquals(stats.errorsNumber(), 0);

        tearDown();
    }

    private void createZip(String[] files, String name) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(
                new FileOutputStream(sourceDir + File.separator + name))) {
            for (String file : files) {
                zos.putNextEntry(new ZipEntry(file));
            }
        }
    }

    private void createFile(String name) throws IOException {
        File file = new File(sourceDir + File.separator + name);
        //noinspection ResultOfMethodCallIgnored
        new File(file.getParent()).mkdirs();
        assertTrue(file.createNewFile());
    }

    private void deleteFile(String name) {
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