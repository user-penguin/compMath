public class UklMatrix extends Matrix {

    UklMatrix(int size, int k, int l, double alpha, double betta) {
        matrix = new double[size][size];

        for(int i = 0; i < size; i++)
            matrix[i][i] = 1;

        matrix[k][k] = matrix[l][l] = alpha;
        matrix[k][l] = -1 * betta;
        matrix[l][k] = betta;
    }

}
