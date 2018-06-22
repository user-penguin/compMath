public class UnitMatrix extends Matrix {

    // constructors

    public UnitMatrix(int size) {
        super();
        matrix = new double[size][size];
        for (int i = 0; i < size; i++)
            matrix[i][i] = 1;
    }

}
