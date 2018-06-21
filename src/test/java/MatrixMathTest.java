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
        assertEquals(expected, true);
    }

    @Test
    void compareVectors2() {
        boolean expected = MatrixMath.compareVectors(new double[]{1 + Math.E * 2, 2, 3}, new double[]{1, 2, 3});
        assertEquals(expected, false);
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
        double[] firstApproximate = MatrixMath.getRandomVector(matrix.length);
        double[] expected = new MatrixMath().searchPersonalVector(matrix, firstApproximate);
        double[] actual = MatrixMath.normalization(new double[]{1, -2, 2});
        assertEquals(MatrixMath.compareVectors(expected, actual) ||
                MatrixMath.compareVectors(MatrixMath.multipVectorNumber(expected, -1), actual), true);
    }

    @Test
    void searchPersonalNumber() {
        double[][] matrix = new double[][]{{2, 2, -8}, {2, -7, 10}, {-8, 10, -4}};
        double[] firstApproximate = MatrixMath.getRandomVector(matrix.length);
        double expected = MatrixMath.searchPersonalNumber(matrix, MatrixMath.searchPersonalVector(matrix, firstApproximate));
        double actual = -18;
        assertEquals(MatrixMath.equals(expected, actual), true);
    }

    @Test
    void searchSecondPersonalNumber() {
        double[][] matrix = new double[][]{{2, 2, -8}, {2, -7, 10}, {-8, 10, -4}};
        double[] firstApproximate = MatrixMath.getRandomVector(matrix.length);
        double[] persVecSec = MatrixMath.searchSecPersVector(matrix,
                MatrixMath.searchPersonalVector(matrix, firstApproximate), firstApproximate);
        double expected = MatrixMath.searchPersonalNumber(matrix, persVecSec);
        double actual = 9;
        assertEquals(MatrixMath.equals(expected, actual), true);
    }

    @Test
    void scalarMultipVectorTest() {
        double[] vec1 = new double[]{2, 3, 8};
        double[] vec2 = new double[]{4, 5, -2};
        double expected = MatrixMath.scalarMultipVector(vec1, vec2);
        double actual = 7;
        assertEquals(expected, actual);
    }

    @Test
    void substract() {
        double[] vec1 = new double[]{2, 3, 8};
        double[] vec2 = new double[]{4, 5, -2};
        double[] expected = MatrixMath.substract(vec1, vec2);
        double[] actual = new double[]{-2, -2, 10};
        assertArrayEquals(expected, actual);
    }

    @Test
    void transposeMatrix() {
        double[][] matrix = new double[][]{{1, 2, 3}, {4, 5, 6}, {-7, -8, 9}};
        MatrixMath mM = new MatrixMath();
        double[][] expected = new MatrixMath().transposeMatrix(matrix);
        double[][] actual = new double[][]{{1, 4, -7}, {2, 5, -8}, {3, 6, 9}};

        for(int i = 0; i < matrix.length; i++)
            assertArrayEquals(expected[i], actual[i]);
    }

    @Test
    void multipMatrixMatrixTest() {
        Matrix A = new Matrix(new double[][]{{2, 3}, {4, 5}, {6, 7}});
        Matrix B = new Matrix(new double[][]{{1, 2, 3}, {4, 2, 3}});
        Matrix expected = new Matrix(MatrixMath.multipMatrixMatrix(A.getMatrix(), B.getMatrix()));
        Matrix actual = new Matrix(new double[][]{{14, 10, 15}, {24, 18, 27}, {34, 26, 39}});
        for (int i = 0; i < actual.getMatrix().length; i++)
            assertArrayEquals(expected.getMatrix()[i], actual.getMatrix()[i]);
    }

}