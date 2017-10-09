import com.sun.org.glassfish.external.statistics.Statistic;

import java.io.*;

public class ZipApp {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: <source> <regex> <destination>");
            return;
        }
        Extractor extractor = new Extractor();
        try {
            extractor.extractFiles(args[0], args[1], args[2]);
        } catch (IOException e) {
            System.out.println("Source folder is illegal");
            return;
        }

        Extractor.Statistic stats = extractor.getStats();
        if (stats.errorsNumber() != 0) {
            System.out.println("Extraction was failed with "
                    + stats.errorsNumber() + " errors");
            return;
        }
        System.out.println("Extraction was successful");
        System.out.println(stats.extractedFiles().size() + " files were extracted:");
        for (String name : stats.extractedFiles()) {
            System.out.println(name);
        }
        System.out.println(stats.failedExtractions() +
                " file were not extracted for the cause of errors.");
    }

}
