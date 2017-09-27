import java.util.Arrays;
import java.util.Comparator;

public class StrangeMatrix {

    /**
     * This method returns two-dimensional array in String form.
     * Elements will be added in a spiral sequence. Comma is used as delimiter.
     * For example
     * <p> 9 2 3
     * <p> 8 1 4
     * <p> 7 6 5
     * <p>
     * returns [1, 2, 3, 4, 5, 6, 7, 8, 9]
     * <p>
     * Array must be completed quad matrix N x N with odd N.
     * All columns must have same odd sizes. If they do not, IllegalArgumentException will be thrown.
     * @param data two-dimensional array to work with
     * @return String representation of your array
     */
    public static String spiralPrint(int[][] data) {
        if (data == null)
            throw new IllegalArgumentException();

        int n = data.length;

        if (n % 2 != 1) {
            throw new IllegalArgumentException();
        }

        if (!isMatrixRectangular(data, n)) {
            throw new IllegalArgumentException();
        }

        StringBuilder sb = new StringBuilder();
        int center = n / 2;
        sb.append(data[center][center]);
        for (int step = 1; step <= center; step++) {
            printLine(data, sb, center - step, center - step + 1, center - step, center + step);
            printLine(data, sb, center - step + 1, center + step, center + step, center + step);
            printLine(data, sb, center + step, center + step - 1, center + step, center - step);
            printLine(data, sb, center + step - 1, center - step, center - step, center - step);
        }
        return sb.toString();
    }

    /**
     * Sorts columns of given two-dimensional array by the first row.
     * Sort is guaranteed to be stable, columns with equal first elements will not be reordered.
     * <p>
     * The array may have 0 columns or 0 rows.
     * All columns must have the same size. If not, IllegalArgumentException will be thrown.
     * Also all arrays must be not null. If they are not, IllegalArgumentException will be thrown.
     * <p>
     * This method takes additional memory equals to the size of data, but it takes nm + sort(n)
     * time instead of m*sort(n)
     * @param data two-dimensional array to sort
     */
    public static void sortByColumns(int[][] data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }

        int n = data.length;
        if (n == 0) {
            return;
        }

        if (data[0] == null) {
            throw new IllegalArgumentException();
        }
        int m = data[0].length;

        if (!isMatrixRectangular(data, m)) {
            throw new IllegalArgumentException();
        }

        if (m == 0)
            return;

        int[][] dataCopy = new int[m][n];
        copyRotateTwoDimensionalArray(data, dataCopy, n, m);

        Arrays.sort(dataCopy, Comparator.comparingInt(ints -> ints[0]));

        copyRotateTwoDimensionalArray(dataCopy, data, m, n);
    }

    /**
     * Checks if passed array has rectangular form and all inner arrays are not equal to null.
     * More formal, checks that all columns has same sizes equals to expectedSize.
     * @param data array to check
     * @param expectedSize expected column size
     * @return true if every column has equal to expectedSize size and false otherwise
     */
    private static boolean isMatrixRectangular(int[][] data, int expectedSize) {
        for (int[] aData : data) {
            if (aData == null || aData.length != expectedSize) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method appends line of data elements to the end of StringBuilder.
     * It starts with (beginX, beginY) point and ends with (endX, endY) point.
     * <p>
     * Lane must be horizontal or vertical. If it is not, IllegalArgumentException will be thrown.
     */
    private static void printLine(int[][] data, StringBuilder sb, int beginX, int beginY, int endX, int endY) {
        if (beginX != endX && beginY != endY)
            throw new IllegalArgumentException();

        if (beginX == endX) {
            int dy = sign(endY - beginY);
            for (int y = beginY; y != endY + dy; y += dy) {
                sb.append(", ").append(data[beginX][y]);
            }
        } else {
            int dx = sign(endX - beginX);
            for (int x = beginX; x != endX + dx; x += dx) {
                sb.append(", ").append(data[x][beginY]);
            }
        }
    }

    private static int sign(int x) {
        return Integer.compare(x, 0);
    }

    private static void copyRotateTwoDimensionalArray(int[][] source, int[][] dest, int n, int m) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                dest[j][i] = source[i][j];
            }
        }
    }
}







