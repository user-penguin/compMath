import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Lab_8Test {

    @Test
    void calcInaccuracy() {
        double[] a = new double[]{1, 2, 3, 5.5};
        double[] b = new double[]{1, 0, 4, 5};
        double[] expected = MatrixMath.calcInaccuracy(a, b);
        double[] actual = new double[]{0, 2, 1, 0.5};
        assertArrayEquals(expected, actual);
    }
}