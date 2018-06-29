import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SplineMethodsTest {

    @Test
    void getTypeOfFunct() {
        SplineMethods SM = new SplineMethods("sin(x)");
        String expected = SM.getTypeOfFunct();
        String actual = "sin97(x)";
        assertEquals(expected.equals(actual), true);
    }

    @Test
    void transformListToArray() {
        ArrayList<Double> source = new ArrayList<>();
        source.add(2.0);
        source.add(3.0);
        double[] expected = new SplineMethods().transformListToArray(source);
        double[] actual = new double[]{2, 3};

        assertArrayEquals(expected, actual);
    }

    @Test
    void tridiagonalMethod() {
        double[][] sourceMatrix = new double[][]{
                {1, 2, 0, 0},
                {2, 3, 4, 0},
                {0, 5, 6, 7},
                {0, 0, 1, 3}
        };
        double[] freeIndexes = new double[]{1, 1, 2, 2};
        SplineMethods  SM = new SplineMethods();
        SM.setFreeB(freeIndexes);
        double[] expected = SM.tridiagonalMethod(sourceMatrix);
        double[] actual = new double[]{3, 13,  31, 8};
        assertArrayEquals(expected, actual, "666");

    }
}