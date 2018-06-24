import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterpolMethodsTest {


    @Test
    void fillFromFile() {
        String path = "src\\test\\source\\testFillNodes.txt";
        InterpolMethods IM = new InterpolMethods();
        IM.fillFromFile(path);
        double[] expected1 = IM.getNodes();
        double[] actual1 = new double[]{1, 2, 3, 4};

        double[] expected2 = IM.getInterpolateValues();
        double[] actual2 = new double[]{10, 20, 30, 40};

        assertArrayEquals(expected1, actual1);
        assertArrayEquals(expected2, actual2);
    }

    @Test
    void getLagrangePolyn() {
        String path = "src\\test\\source\\testFillNodes.txt";
        InterpolMethods IM = new InterpolMethods();
        IM.fillFromFile(path);

        double expected = IM.getLagrangePolyn(2.5);
        double actual = 25;
        assertEquals(expected, actual);
    }
}