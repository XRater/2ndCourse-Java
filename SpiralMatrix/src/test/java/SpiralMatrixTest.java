import org.junit.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SpiralMatrixTest {

    @Test
    public void testSortByColumnsRegular() {
        int[][] data = {{3}};
        SpiralMatrix.sortByColumns(data);
        assertEquals("[[3]]", Arrays.deepToString(data));

        data = new int[][]{{1, 2, 3}};
        SpiralMatrix.sortByColumns(data);
        assertEquals("[[1, 2, 3]]", Arrays.deepToString(data));

        data = new int[][]{{3, 2, 1}};
        SpiralMatrix.sortByColumns(data);
        assertEquals("[[1, 2, 3]]", Arrays.deepToString(data));

        data = new int[][]{};
        SpiralMatrix.sortByColumns(data);
        assertEquals("[]", Arrays.deepToString(data));

        data = new int[][]{{4, 2, -1, 0}};
        SpiralMatrix.sortByColumns(data);
        assertEquals("[[-1, 0, 2, 4]]", Arrays.deepToString(data));

        data = new int[][]{{3}, {2}, {1}};
        SpiralMatrix.sortByColumns(data);
        assertEquals("[[3], [2], [1]]", Arrays.deepToString(data));

        data = new int[][]{{1}, {2}, {3}};
        SpiralMatrix.sortByColumns(data);
        assertEquals("[[1], [2], [3]]", Arrays.deepToString(data));

        data = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        SpiralMatrix.sortByColumns(data);
        assertEquals("[[1, 2, 3], [4, 5, 6], [7, 8, 9]]", Arrays.deepToString(data));

        data = new int[][]{{3, 1, 2}, {4, 5, 6}};
        SpiralMatrix.sortByColumns(data);
        assertEquals("[[1, 2, 3], [5, 6, 4]]", Arrays.deepToString(data));

        data = new int[][]{{6, 5, 4}, {1, 2, 3}, {7, 8, 9}, {1, 2, 3}, {3, 1, 2}};
        SpiralMatrix.sortByColumns(data);
        assertEquals("[[4, 5, 6], [3, 2, 1], [9, 8, 7], [3, 2, 1], [2, 1, 3]]", Arrays.deepToString(data));

        data = new int[][]{{0, 0, 0, 0}, {1, 2, 3, 4}};
        SpiralMatrix.sortByColumns(data);
        assertEquals("[[0, 0, 0, 0], [1, 2, 3, 4]]", Arrays.deepToString(data));

        data = new int[][]{{4, 0, 0, 3}, {1, 2, 3, 4}};
        SpiralMatrix.sortByColumns(data);
        assertEquals("[[0, 0, 3, 4], [2, 3, 4, 1]]", Arrays.deepToString(data));

        data = new int[][]{{4, 3, 2, 1}};
        SpiralMatrix.sortByColumns(data);
        assertEquals("[[1, 2, 3, 4]]", Arrays.deepToString(data));
    }
}