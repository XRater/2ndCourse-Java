import java.util.Arrays;
import java.util.Comparator;

/**
 * Class contains some static methods to work with rectangular two-dimensional arrays (matrix).
 * It is not possible to create object of that class. All functions must be used in a static way.
 */
public class StrangeMatrix {

    private StrangeMatrix() {

    }

    /**
     * This method returns two-dimensional array in a String form.
     * Elements will be taken in a spiral sequence clock-wise.
     * Comma is used as delimiter.
     * For example
     * <table>
     * <tr>
     * <td> 9 </td> <td> 2 </td> <td> 3 </td>
     * </tr>
     * <tr>
     * <td> 8 </td> <td> 1 </td> <td> 4 </td>
     * </tr>
     * <tr>
     * <td> 7 </td> <td> 6 </td> <td> 5 </td>
     * </tr>
     * </table>
     *
     * returns [1, 2, 3, 4, 5, 6, 7, 8, 9]
     * <p>
     * Array must be a completed square N x N matrix where N is odd.
     * If it does not, IllegalArgumentException will be thrown.
     * @param data two-dimensional array
     * @return String representation of the array
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
            appendLine(data, sb, center - step, center - step + 1, center - step, center + step);
            appendLine(data, sb, center - step + 1, center + step, center + step, center + step);
            appendLine(data, sb, center + step, center + step - 1, center + step, center - step);
            appendLine(data, sb, center + step - 1, center - step, center - step, center - step);
        }
        return sb.toString();
    }

    /**
     * Sorts columns of the given two-dimensional array by the first element of each column.
     * Sort is guaranteed to be stable, columns with equal first elements will not be reordered.
     * <p>
     * The array may have 0 columns or 0 rows.
     * All columns must have the same size. If not, IllegalArgumentException will be thrown.
     * Also all arrays must not be null. If they are, IllegalArgumentException will be thrown.
     * <p>
     * This method takes additional memory that is equal to the size of the data and takes nm + sort(n)
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
     * The method checks if given array has rectangular form and all inner arrays are not null.
     * In other words, it checks that all columns have the same size equals to expectedSize.
     * @param data input array
     * @param expectedSize expected column size
     * @return true if every column has size equal to expectedSize and false otherwise
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
     * The method appends line of data elements to the end of StringBuilder.
     * The line starts with (beginX, beginY) point and ends with (endX, endY) point.
     * <p>
     * Line must be horizontal or vertical. If it is not, IllegalArgumentException will be thrown.
     */
    private static void appendLine(int[][] data, StringBuilder sb, int beginX, int beginY, int endX, int endY) {
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







