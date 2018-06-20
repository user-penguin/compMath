import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {

    @org.junit.jupiter.api.Test
    void fillFromFile() {
        Matrix m = new Matrix();
        m.fillFromFile("/home/dim/workspace/tests/in41.txt");

        double[][] expected = m.getMatrix();
        double[][] actual = new double[][]{
            {2.2, 1, 0.5, 2},
            {1, 1.3, 2, 1},
            {0.5, 2, 0.5, 1.6},
            {2, 1, 1.6, 2}
        };

        assertEquals(expected.length, actual.length);
        for(int i = 0; i < actual.length; i++)
            assertArrayEquals(expected[i], actual[i]);
    }

    @Test
    void fillFromFile1() {
        Matrix m = new Matrix();
        m.fillFromFile("/home/dim/workspace/tests/in42.txt");

        double[][] expected = m.getMatrix();
        double[][] actual = new double[][]{
            {2.2, 1, 0.5, 2, 6},
            {1, 1.3, 2, 1, 7},
            {0.5, 2, 0.5, 1.6, 8.6},
            {2, 1, 1.6, 2, 7.8},
        };

        assertEquals(expected.length, actual.length);
        for(int i = 0; i < actual.length; i++)
            assertArrayEquals(expected[i], actual[i]);
    }

    @Test
    void fillFromFile2() {
        Matrix m = new Matrix();
        m.fillFromFile("/home/dim/workspace/tests/in43.txt");

        double[][] expected = m.getMatrix();
        double[][] actual = new double[][]{{1}, {2}, {3}, {5.8}};

        assertEquals(expected.length, actual.length);
        for(int i = 0; i < actual.length; i++)
            assertArrayEquals(expected[i], actual[i]);
    }
}