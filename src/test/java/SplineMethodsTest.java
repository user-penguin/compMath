import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SplineMethodsTest {

    @Test
    void getTypeOfFunct() {
        SplineMethods SM = new SplineMethods("sin(x)");
        String expected = SM.getTypeOfFunct();
        String actual = "sin(x)";
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
}