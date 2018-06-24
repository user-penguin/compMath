import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {

    @Test
    void fillFromFile() {
        Matrix expectedMatrix = new Matrix("src\\test\\source\\in41.txt");
        Matrix actualMatrix = new Matrix(new double[][]{
            {2.2, 1, 0.5, 2},
            {1, 1.3, 2, 1},
            {0.5, 2, 0.5, 1.6},
            {2, 1, 1.6, 2}
        });
        double[][] expected = expectedMatrix.getMatrix();
        double[][] actual = actualMatrix.getMatrix();
        assertEquals(expected.length, actual.length);
        for(int i = 0; i < actual.length; i++)
            assertArrayEquals(expected[i], actual[i]);
    }

    @Test
    void transpose() {
        Matrix expectedMatrix = new Matrix(new double[][]{
                {1, 2, 3},
                {4, 5, 6},
                {-7, -8, 9}
        });
        expectedMatrix = new Matrix(expectedMatrix.transpose());
        Matrix actualMatrix = new Matrix(new double[][]{
                {1, 4, -7},
                {2, 5, -8},
                {3, 6, 9}
        });
        double[][] expected = expectedMatrix.getMatrix();
        double[][] actual = actualMatrix.getMatrix();
        for(int i = 0; i < expected.length; i++)
            assertArrayEquals(expected[i], actual[i]);
    }
}