public class SpiralMatrix {

    private int[][] data;
    private final int N;

    SpiralMatrix(int[][] data) {
        N = data.length;

        for (int[] aData : data) {
            if (aData.length != N) {
                throw new IllegalArgumentException();
            }
        }
        this.data = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.data[i][j] = data[j][i];
            }
        }

    }

}
