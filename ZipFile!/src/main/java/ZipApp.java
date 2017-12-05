import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * This class is a console utility for extracting files from zips.
 * Application takes source folder, regex, destination folder and then
 * extracts files that matches regex from every zip in the source folder
 * to the destination folder.
 * <p>
 * Files will be extracted recursively by folders.
 */
public class ZipApp {

    /**
     * Main activity.
     * <p>
     * Usage: source regex destination
     */
    public static void main(@NotNull final String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: <source> <regex> <destination>");
            return;
        }
        final Extractor extractor = new Extractor();
        try {
            extractor.extractFiles(args[0], args[1], args[2]);
        } catch (final IOException e) {
            System.out.println("Source folder is illegal");
            return;
        }

        final Extractor.Statistic stats = extractor.getStats();
        if (stats.errorsNumber() != 0) {
            System.out.println("Extraction was failed with "
                    + stats.errorsNumber() + " errors");
            return;
        }
        System.out.println("Extraction was successful");
        System.out.println(stats.extractedFiles().size() + " files were extracted:");
        for (final String name : stats.extractedFiles()) {
            System.out.println(name);
        }
        System.out.println(stats.failedExtractions() +
                " file were not extracted for the cause of errors.");
    }

}
