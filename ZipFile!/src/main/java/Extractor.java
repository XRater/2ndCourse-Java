import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@SuppressWarnings("WeakerAccess")
public class Extractor {

    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private final int bufferSize;
    private final Statistic stats = new Statistic();
    private final byte[] buffer;

    public Extractor() {
        bufferSize = DEFAULT_BUFFER_SIZE;
        buffer = new byte[bufferSize];
    }

    public Extractor(int bufferSize) {
        this.bufferSize = bufferSize;
        buffer = new byte[bufferSize];
    }

    public Statistic getStats() {
        return stats;
    }

    public void resetStats() {
        stats.reset();
    }

    public void extractFiles(@NotNull String path, @NotNull String regex, String dest) throws IOException {
        File folder = new File(path);
        if (!folder.isDirectory()) {
            throw new IOException();
        }
        //noinspection ConstantConditions
        for (File f : folder.listFiles()) {
            String name = f.getAbsolutePath();
            if (isLegalZip(name)) {
                try {
                    unzip(name, regex, dest);
                } catch (IOException e) {
                    stats.errorsNumber++;
                }
            }
        }
    }

    /**
     * The method extracts all files matches regex patter from the given zip to the destination directory.
     * @param zipName absolute path to zte zip
     * @param regex regex to match
     * @param dest destination folder
     * @throws IOException if an I/O error occurs
     */
    private void unzip(@NotNull String zipName, @NotNull String regex, String dest) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipName))) {
            for (ZipEntry ze = zis.getNextEntry(); ze != null; ze = zis.getNextEntry()) {
                if (ze.isDirectory()) {
                    continue;
                }
                String name = ze.getName();
                if (!Pattern.matches(regex, name)) {
                    continue;
                }
                try {
                    extractFile(dest, zis, name);
                } catch (IOException e) {
                    stats.failedExtractions++;
                }
            }
        }
    }

    private void extractFile(String dest, ZipInputStream zis, String name) throws IOException {
        File newFile = new File(dest + File.separator + name);
        //noinspection ResultOfMethodCallIgnored
        new File(newFile.getParent()).mkdirs();
        FileOutputStream fos = new FileOutputStream(newFile);
        int len;
        while ((len = zis.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
        }
        stats.extractedFiles.add(name);
    }

    /**
     * Checks if the name argument corresponds to the existing zip file.
     * Also user must have reading rights for the file.
     * @param name absolute path to the zip file
     * @return true if the zip file is legal and false otherwise
     */
    private static boolean isLegalZip(@NotNull String name) {
        try (DataInputStream is = new DataInputStream(new FileInputStream(new File(name)))) {
            if (is.available() <= 4) {
                return false;
            }
            int prefix = is.readInt();
            return (prefix == 0x504b0304 || prefix == 0x504b0506 || prefix == 0x504b0708);
        } catch (IOException e) {
            return false;
        }
    }

    public static class Statistic {
        private ArrayList<String> extractedFiles = new ArrayList<>();
        private int errorsNumber;
        private int failedExtractions;

        /**
         * @return list of pathes to extracted files from destination folder.
         */
        public ArrayList<String> extractedFiles() {
            return extractedFiles;
        }

        /**
         * @return number of errors occurred
         */
        public int errorsNumber() {
            return errorsNumber;
        }

        /**
         * @return number of files witch extraction was failed
         */
        public int failedExtractions() {
            return failedExtractions;
        }

        private void reset() {
            extractedFiles.clear();
            errorsNumber = 0;
            failedExtractions = 0;
        }
    }
}
