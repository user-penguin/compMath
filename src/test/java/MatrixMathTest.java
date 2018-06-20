import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixMathTest {

    @Test
    void multipMatrix() {
        MatrixMath m = new MatrixMath();
        double[][] A = new double[][]{{2, 1}, {3, 4}};
        double[] vector = new double[]{7, 8};
        double[] expected = m.multipMatrixVector(A, vector);
        double[] actual = new double[]{22, 53};

        assertArrayEquals(expected, actual);
    }

    @Test
    void compareVectors1() {
        boolean expected = new MatrixMath().compareVectors(new double[]{1, 2, 3}, new double[]{1, 2, 3});
        boolean actual = true;
        assertEquals(expected, actual);
    }

    @Test
    void compareVectors2() {
        boolean expected = new MatrixMath().compareVectors(new double[]{1 + Math.E * 2, 2, 3}, new double[]{1, 2, 3});
        boolean actual = false;
        assertEquals(expected, actual);
    }

    @Test
    void normalizationTest() {
        double[] expected = new MatrixMath().normalization(new double[]{1, 2, -3, 4, 5});
        double[] actual = new double[]{0.2, 0.4, -0.6, 0.8, 1};
        assertArrayEquals(expected, actual);
    }
}