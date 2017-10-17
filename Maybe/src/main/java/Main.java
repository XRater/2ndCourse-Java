import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: <source> <dest>");
            return;
        }
        try {
            FileInputStream fis = new FileInputStream(new File(args[0]));
        } catch (FileNotFoundException e) {
            System.out.println("Error. File does not exist or not enouth permissions");
        }
    }

}
