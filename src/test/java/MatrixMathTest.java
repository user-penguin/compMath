import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixMathTest {

    @Test
    void multipMatrix() {
        double[][] A = new double[][]{{2, 1}, {3, 4}};
        double[] vector = new double[]{7, 8};
        double[] expected = MatrixMath.multipMatrixVector(A, vector);
        double[] actual = new double[]{22, 53};

        assertArrayEquals(expected, actual);
    }

    @Test
    void compareVectors1() {
        boolean expected = MatrixMath.compareVectors(new double[]{1, 2, 3}, new double[]{1, 2, 3});
        boolean actual = true;
        assertEquals(expected, actual);
    }

    @Test
    void compareVectors2() {
        boolean expected = MatrixMath.compareVectors(new double[]{1 + Math.E * 2, 2, 3}, new double[]{1, 2, 3});
        boolean actual = false;
        assertEquals(expected, actual);
    }

    @Test
    void normalizationTest() {
        double[] expected = new MatrixMath().normalization(new double[]{1, 2, -3, 4, 5});
        double[] actual = new double[]{0.2, 0.4, -0.6, 0.8, 1};
        assertArrayEquals(expected, actual);
    }

    @Test
    void searchPersonalVectorTest() {
        double[][] matrix = new double[][]{{2, 2, -8}, {2, -7, 10}, {-8, 10, -4}};
        double[] expected = new MatrixMath().searchPersonalVector(matrix, new Double(1));
        double[] actual = MatrixMath.normalization(new double[]{1, -2, 2});
        assertEquals(MatrixMath.compareVectors(expected, actual) ||
                MatrixMath.compareVectors(MatrixMath.multipVectorNumber(expected, -1), actual), true);
    }

    @Test
    void scalarMultipVectorTest() {
        double[] vec1 = new double[]{2, 3, 8};
        double[] vec2 = new double[]{4, 5, -2};
        double expected = MatrixMath.scalarMultipVector(vec1, vec2);
        double actual = 7;
        assertEquals(expected, actual);
    }
}