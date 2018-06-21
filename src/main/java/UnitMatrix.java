public class UnitMatrix extends Matrix {

    UnitMatrix(int size) {
        matrix = new double[size][size];
        for (int i = 0; i < size; i++)
            matrix[i][i] = 1;
    }

}
