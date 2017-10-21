import org.junit.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Class contains tests for StrangeMatrix class
 */
public class StrangeMatrixTest {

    @Test
    public void testSortByColumnsRegular() {
        int[][] data = {{3}};
        StrangeMatrix.sortByColumns(data);
        assertEquals("[[3]]", Arrays.deepToString(data));

        data = new int[][]{{1, 2, 3}};
        StrangeMatrix.sortByColumns(data);
        assertEquals("[[1, 2, 3]]", Arrays.deepToString(data));

        data = new int[][]{{3, 2, 1}};
        StrangeMatrix.sortByColumns(data);
        assertEquals("[[1, 2, 3]]", Arrays.deepToString(data));

        data = new int[][]{};
        StrangeMatrix.sortByColumns(data);
        assertEquals("[]", Arrays.deepToString(data));

        data = new int[][]{{4, 2, -1, 0}};
        StrangeMatrix.sortByColumns(data);
        assertEquals("[[-1, 0, 2, 4]]", Arrays.deepToString(data));

        data = new int[][]{{3}, {2}, {1}};
        StrangeMatrix.sortByColumns(data);
        assertEquals("[[3], [2], [1]]", Arrays.deepToString(data));

        data = new int[][]{{1}, {2}, {3}};
        StrangeMatrix.sortByColumns(data);
        assertEquals("[[1], [2], [3]]", Arrays.deepToString(data));

        data = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        StrangeMatrix.sortByColumns(data);
        assertEquals("[[1, 2, 3], [4, 5, 6], [7, 8, 9]]", Arrays.deepToString(data));

        data = new int[][]{{3, 1, 2}, {4, 5, 6}};
        StrangeMatrix.sortByColumns(data);
        assertEquals("[[1, 2, 3], [5, 6, 4]]", Arrays.deepToString(data));

        data = new int[][]{{6, 5, 4}, {1, 2, 3}, {7, 8, 9}, {1, 2, 3}, {3, 1, 2}};
        StrangeMatrix.sortByColumns(data);
        assertEquals("[[4, 5, 6], [3, 2, 1], [9, 8, 7], [3, 2, 1], [2, 1, 3]]", Arrays.deepToString(data));

        data = new int[][]{{0, 0, 0, 0}, {1, 2, 3, 4}};
        StrangeMatrix.sortByColumns(data);
        assertEquals("[[0, 0, 0, 0], [1, 2, 3, 4]]", Arrays.deepToString(data));

        data = new int[][]{{4, 0, 0, 3}, {1, 2, 3, 4}};
        StrangeMatrix.sortByColumns(data);
        assertEquals("[[0, 0, 3, 4], [2, 3, 4, 1]]", Arrays.deepToString(data));

        data = new int[][]{{4, 3, 2, 1}};
        StrangeMatrix.sortByColumns(data);
        assertEquals("[[1, 2, 3, 4]]", Arrays.deepToString(data));

        data = new int[][]{{}, {}, {}, {}};
        StrangeMatrix.sortByColumns(data);
        assertEquals("[[], [], [], []]", Arrays.deepToString(data));

        data = new int[][]{{}};
        StrangeMatrix.sortByColumns(data);
        assertEquals("[[]]", Arrays.deepToString(data));
    }

    @Test
    public void testSortByColumnsExceptions() {
        assertThrows(IllegalArgumentException.class,
                () -> StrangeMatrix.sortByColumns(null));

        assertThrows(IllegalArgumentException.class,
                () -> StrangeMatrix.sortByColumns(new int[][]{{1, 2}, {}}));

        assertThrows(IllegalArgumentException.class,
                () -> StrangeMatrix.sortByColumns(new int[][]{{}, {1, 2}, {3, 4}}));

        assertThrows(IllegalArgumentException.class,
                () -> StrangeMatrix.sortByColumns(new int[][]{{}, {}, {}, {}, {}, {3, 4}}));

        assertThrows(IllegalArgumentException.class,
                () -> StrangeMatrix.sortByColumns(new int[][]{{1, 2}, {1, 2}, {3, 4, 5}}));

        assertThrows(IllegalArgumentException.class,
                () -> StrangeMatrix.sortByColumns(new int[][]{{1, 2}, null}));

        assertThrows(IllegalArgumentException.class,
                () -> StrangeMatrix.sortByColumns(new int[][]{null, null}));

        assertThrows(IllegalArgumentException.class,
                () -> StrangeMatrix.sortByColumns(new int[][]{null}));
    }

    @Test
    public void testSpiralPrintRegular() {
        int[][] data = {{1}};
        assertEquals("1", StrangeMatrix.spiralPrint(data));

        data = new int[][]{ {9, 2, 3},
                            {8, 1, 4},
                            {7, 6, 5}};
        assertEquals("1, 2, 3, 4, 5, 6, 7, 8, 9", StrangeMatrix.spiralPrint(data));

        data = new int[][]{ {25, 10, 11, 12, 13},
                            {24, 9, 2, 3, 14},
                            {23, 8, 1, 4, 15},
                            {22, 7, 6, 5, 16},
                            {21, 20, 19, 18, 17}};
        assertEquals("1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25",
                StrangeMatrix.spiralPrint(data));
     }

    @Test
    public void testSpiralPrintExceptions() {
        assertThrows(IllegalArgumentException.class,
                () -> StrangeMatrix.spiralPrint(null));

        assertThrows(IllegalArgumentException.class,
                () -> StrangeMatrix.spiralPrint(new int[][]{{1, 2}, {}}));

        assertThrows(IllegalArgumentException.class,
                () -> StrangeMatrix.spiralPrint(new int[][]{{}, {1, 2}, {3, 4}}));

        assertThrows(IllegalArgumentException.class,
                () -> StrangeMatrix.spiralPrint(new int[][]{{}, {}, {}, {}, {}, {3, 4}}));

        assertThrows(IllegalArgumentException.class,
                () -> StrangeMatrix.spiralPrint(new int[][]{{1, 2}, {1, 2}, {3, 4, 5}}));

        assertThrows(IllegalArgumentException.class,
                () -> StrangeMatrix.spiralPrint(new int[][]{{1, 2}, null}));

        assertThrows(IllegalArgumentException.class,
                () -> StrangeMatrix.spiralPrint(new int[][]{null, null}));

        assertThrows(IllegalArgumentException.class,
                () -> StrangeMatrix.spiralPrint(new int[][]{}));

        assertThrows(IllegalArgumentException.class,
                () -> StrangeMatrix.spiralPrint(new int[][]{{}}));

        assertThrows(IllegalArgumentException.class,
                () -> StrangeMatrix.spiralPrint(new int[][]{{1, 2, 3}}));

        assertThrows(IllegalArgumentException.class,
                () -> StrangeMatrix.spiralPrint(new int[][]{{1, 2}}));

        assertThrows(IllegalArgumentException.class,
                () -> StrangeMatrix.spiralPrint(new int[][]{{1, 2, 3}, {4, 5, 6}}));

        assertThrows(IllegalArgumentException.class,
                () -> StrangeMatrix.spiralPrint(new int[][]{{1, 2}, {3, 4}}));

        assertThrows(IllegalArgumentException.class,
                () -> StrangeMatrix.spiralPrint(new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0},
                                                            {0, 0, 0, 0}, {0, 0, 0, 0}}));
    }
}