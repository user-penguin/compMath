public class UklMatrix extends Matrix {

    // constructors

    UklMatrix(int size, int k, int l) {
        matrix = new double[size][size];

        for(int i = 0; i < size; i++)
            matrix[i][i] = 1;

        matrix[k][k] = matrix[l][l] = calculateAlpha(k, l);
        matrix[k][l] = -1 * calculateBetta(k, l);
        matrix[l][k] = calculateBetta(k, l);
    }

}
