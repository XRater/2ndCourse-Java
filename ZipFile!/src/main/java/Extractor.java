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
    @NotNull
    private final byte[] buffer;

    public Extractor() {
        bufferSize = DEFAULT_BUFFER_SIZE;
        buffer = new byte[bufferSize];
    }

    public Extractor(final int bufferSize) {
        this.bufferSize = bufferSize;
        buffer = new byte[bufferSize];
    }

    @NotNull
    public Statistic getStats() {
        return stats;
    }

    public void resetStats() {
        stats.reset();
    }

    public void extractFiles(@NotNull final String path, @NotNull final String regex, final String dest) throws IOException {
        final File folder = new File(path);
        if (!folder.isDirectory()) {
            throw new IOException();
        }
        //noinspection ConstantConditions
        for (final File f : folder.listFiles()) {
            final String name = f.getAbsolutePath();
            if (isLegalZip(name)) {
                try {
                    unzip(name, regex, dest);
                } catch (final IOException e) {
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
    private void unzip(@NotNull final String zipName, @NotNull final String regex, final String dest) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipName))) {
            for (ZipEntry ze = zis.getNextEntry(); ze != null; ze = zis.getNextEntry()) {
                if (ze.isDirectory()) {
                    continue;
                }
                final String fullName = ze.getName();
                final String name = getFileName(fullName);
                if (!Pattern.matches(regex, name)) {
                    continue;
                }
                try {
                    extractFile(dest, zis, fullName);
                } catch (final IOException e) {
                    stats.failedExtractions++;
                }
            }
        }
    }

    private void extractFile(final String dest, @NotNull final ZipInputStream zis, final String name) throws IOException {
        final File newFile = new File(dest + File.separator + name);
        //noinspection ResultOfMethodCallIgnored
        new File(newFile.getParent()).mkdirs();
        try (FileOutputStream fos = new FileOutputStream(newFile)) {
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        }
        stats.extractedFiles.add(name);
    }

    /**
     * Checks if the name argument corresponds to the existing zip file.
     * Also user must have reading rights for the file.
     * @param name absolute path to the zip file
     * @return true if the zip file is legal and false otherwise
     */
    private static boolean isLegalZip(@NotNull final String name) {
        try (DataInputStream is = new DataInputStream(new FileInputStream(new File(name)))) {
            if (is.available() <= 4) {
                return false;
            }
            final int prefix = is.readInt();
            return (prefix == 0x504b0304 || prefix == 0x504b0506 || prefix == 0x504b0708);
        } catch (final IOException e) {
            return false;
        }
    }

    @NotNull
    private String getFileName(@NotNull final String fullName) {
        final int lastSeparator = fullName.lastIndexOf(File.separatorChar);
        return fullName.substring(lastSeparator + 1);
    }

    /**
     * Class contains stats corresponded to extracting process.
     * <p>
     * You may get stats by calling extractor.getStats() method and reset it with
     * extractor.reset() method.
     * <p>
     * Class contains:
     * <table>
     *     <tr>
     *         List of successfully extracted files.
     *     </tr>
     *     <tr>
     *         Number of files, witch extraction was failed
     *     </tr>
     *     <tr>
     *         Number of zip for witch errors were occurred.
     *     </tr>
     * </table>
     */
    public static class Statistic {

        @NotNull
        private ArrayList<String> extractedFiles = new ArrayList<>();
        private int errorsNumber;
        private int failedExtractions;

        private Statistic() {}
        /**
         * @return list of pathes to extracted files from destination folder.
         */
        @NotNull
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
