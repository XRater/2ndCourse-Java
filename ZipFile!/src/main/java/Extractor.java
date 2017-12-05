import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * This class implements a method to extract files from all zips in the folder.
 * For more information see extractFiles() method.
 */
@SuppressWarnings("WeakerAccess")
public class Extractor {

    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private final int bufferSize;
    private final Statistic stats = new Statistic();

    @NotNull
    private final byte[] buffer;

    /**
     * Base constructor. Sets bufferSize to the default value.
     */
    public Extractor() {
        bufferSize = DEFAULT_BUFFER_SIZE;
        buffer = new byte[bufferSize];
    }

    /**
     * Constructs Extractor object with the given buffer size.
     */
    public Extractor(final int bufferSize) {
        this.bufferSize = bufferSize;
        buffer = new byte[bufferSize];
    }

    /**
     * Returns class that contains extraction statistics
     */
    @NotNull
    public Statistic getStats() {
        return stats;
    }

    /**
     * The method resets statistics.
     */
    public void resetStats() {
        stats.reset();
    }

    /**
     * The method finds all zips in the given folder and recursively extracts every file
     * that matches regex to the destination folder.
     * <p>
     * Also the method collects extraction statistics such as the list of extracted files
     * and number of files that were failed to be extracted.
     * <p>
     * Folder structure will not be affected.
     *
     * @param path  path to the folder with zips
     * @param regex regex to match
     * @param dest  path to the destination folder
     * @throws IOException if the path does not point to the folder or folder does not have read access rights
     */
    public void extractFiles(@NotNull final String path, @NotNull final String regex, final String dest) throws
            IOException {
        resetStats();

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
     * The method extracts all files that match regex pattern from the given zip to the destination folder.
     *
     * @param zipName absolute path to the zip
     * @param regex   regex to match
     * @param dest    destination folder
     * @throws IOException if an I/O error occurs
     */
    private void unzip(@NotNull final String zipName, @NotNull final String regex, final String dest) throws
            IOException {
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

    private void extractFile(final String dest, @NotNull final ZipInputStream zis, final String name) throws
            IOException {
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
     *
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
     * Class contains statistics that corresponds to the extracting process.
     * <p>
     * You can obtain statistics by calling extractor.getStats() method and reset it with
     * the extractor.reset() method.
     * <p>
     * Class contains:
     * <table>
     * <tr>
     * List of successfully extracted files.
     * </tr>
     * <tr>
     * The number of files that failed to be extracted
     * </tr>
     * <tr>
     * The number of zips for which errors have been occurred.
     * </tr>
     * </table>
     */
    public static class Statistic {

        @NotNull
        private final ArrayList<String> extractedFiles = new ArrayList<>();
        private int errorsNumber;
        private int failedExtractions;

        private Statistic() {
        }

        /**
         * Returns list of paths to extracted files relative to the destination folder.
         */
        @NotNull
        public ArrayList<String> extractedFiles() {
            return extractedFiles;
        }

        /**
         * Returns the number of errors occurred
         */
        public int errorsNumber() {
            return errorsNumber;
        }

        /**
         * Returns the number of files that extraction was failed
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
