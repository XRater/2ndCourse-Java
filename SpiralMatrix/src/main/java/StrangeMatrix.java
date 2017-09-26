import java.util.Arrays;
import java.util.Comparator;

public class StrangeMatrix {

    public static void spiralPrint(int[][] data) {
        if (data == null)
            throw new IllegalArgumentException();

        int n = data.length;

        if (n % 2 != 1) {
            throw new IllegalArgumentException();
        }

        if (!isMatrixRectangular(data, n)) {
            throw new IllegalArgumentException();
        }
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

    private static void copyRotateTwoDimensionalArray(int[][] source, int[][] dest, int n, int m) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                dest[j][i] = source[i][j];
            }
        }
    }

}







