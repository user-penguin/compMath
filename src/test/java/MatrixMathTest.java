import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixMathTest {

    @Test
    void multipMatrixVector() {
        double[][] A = new double[][]{
                {2, 1},
                {3, 4}
        };
        double[] vector = new double[]{7, 8};
        double[] expected = MatrixMath.multip(A, vector);
        double[] actual = new double[]{22, 53};
        assertArrayEquals(expected, actual);
    }

    @Test
    void multipMatrixMatrix() {
        Matrix A = new Matrix(new double[][]{
                {2, 3},
                {4, 5},
                {6, 7}
        });
        Matrix B = new Matrix(new double[][]{
                {1, 2, 3},
                {4, 2, 3}
        });
        Matrix expected = new Matrix(MatrixMath.multip(A.getMatrix(), B.getMatrix()));
        Matrix actual = new Matrix(new double[][]{
                {14, 10, 15},
                {24, 18, 27},
                {34, 26, 39}
        });
        for (int i = 0; i < actual.getMatrix().length; i++)
            assertArrayEquals(expected.getMatrix()[i], actual.getMatrix()[i]);
    }

    @Test
    void muilipVectorNumber() {
        double[] vector = new double[]{1, 2, -4};
        int number = 3;
        double[] actual = MatrixMath.multip(vector, number);
        double[] expected = new double[]{3, 6, -12};
        assertArrayEquals(expected, actual);
    }

    @Test
    void multipMatrixNumber() {
        double[][] matrix = new double[][]{
                {3, 0},
                {-5, 7}};
        double b = 3;
        double[][] expected = MatrixMath.multip(matrix, b);
        double[][] actual = new double[][]{
                {9, 0},
                {-15, 21}};
        for (int i = 0; i < matrix.length; i++)
            assertArrayEquals(expected[i], actual[i]);
    }

    @Test
    void scalarMultip() {
        double[] vector1 = new double[]{2, 3, 8};
        double[] vector2 = new double[]{4, 5, -2};
        double expected = MatrixMath.scalarMultip(vector1, vector2);
        double actual = 7;
        assertEquals(expected, actual);
    }

    @Test
    void substract() {
        double[] vector1 = new double[]{2, 3, 8};
        double[] vector2 = new double[]{4, 5, -2};
        double[] expected = MatrixMath.substract(vector1, vector2);
        double[] actual = new double[]{-2, -2, 10};
        assertArrayEquals(expected, actual);
    }

    @Test
    void equals1() {
        boolean expected = MatrixMath.equals(new double[]{1, 2, 3}, new double[]{1, 2, 3});
        assertEquals(expected, true);
    }

    @Test
    void equals2() {
        boolean expected = MatrixMath.equals(new double[]{1 + Math.E * 2, 2, 3}, new double[]{1, 2, 3});
        assertEquals(expected, false);
    }

    @Test
    void searchPersonalVector() {
        double[][] matrix = new double[][]{
                {2, 2, -8},
                {2, -7, 10},
                {-8, 10, -4}};
        double[] firstApproximate = MatrixMath.getRandomVector(matrix.length);
        double[] expected = Lab_4.searchPersonalVector(matrix, firstApproximate);
        double[] actual = MatrixMath.normalization(new double[]{1, -2, 2});
        assertEquals(MatrixMath.equals(expected, actual) ||
                MatrixMath.equals(MatrixMath.multip(expected, -1), actual), true);
    }

    @Test
    void searchPersonalNumber() {
        double[][] matrix = new double[][]{
                {2, 2, -8},
                {2, -7, 10},
                {-8, 10, -4}};
        double[] firstApproximate = MatrixMath.getRandomVector(matrix.length);
        double expected = Lab_4.searchPersonalNumber(matrix, Lab_4.searchPersonalVector(matrix, firstApproximate));
        double actual = -18;
        assertEquals(MatrixMath.equals(expected, actual), true);
    }

    @Test
    void normalization() {
        double[] expected = MatrixMath.normalization(new double[]{1, 2, -3, 4, 5});
        double[] actual = new double[]{0.2, 0.4, -0.6, 0.8, 1};
        assertArrayEquals(expected, actual);
    }

    @Test
    void reverse() {
        double[][] matrix = new double[][]{
                {3, 4},
                {5, 7}};
        double[][] expected = MatrixMath.reverseMatrix(matrix);
        double[][] actual = new double[][]{
                {7.0, -4.0},
                {-5.0, 3.0}};
        for (int i = 0; i < matrix.length; i++)
            assertEquals(MatrixMath.equals(expected[i], actual[i]), true);
    }

    @Test
    void matrixSubstract() {
        double[][] matrix1 = new double[][]{
                {3, 4},
                {5, 7}};
        double[][] matrix2 = new double[][]{
                {1, -2},
                {6, 7}};
        double[][] expected = MatrixMath.substract(matrix1, matrix2);
        double[][] actual = new double[][]{
                {2, 6},
                {-1, 0}};
        for (int i = 0; i < matrix1.length; i++)
            assertArrayEquals(expected[i], actual[i]);
    }

    @Test
    void closerNumber() {
        double[][] matrix = new double[][] {
                {2, 2, -8},
                {2, -7, 10},
                {-8, 10, -4}};
        double closerNum = 20;
        double expected = Lab_4.searchCloserNumber(closerNum, matrix);
        double actual = 9;
        assertEquals(MatrixMath.equals(expected, actual), true);
    }
}