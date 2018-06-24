import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterpolMethodsTest {


    @Test
    void fillFromFile() {
        String path = "src\\test\\source\\testFillNodes.txt";
        InterpolMethods IM = new InterpolMethods();
        IM.fillFromFile(path);
        double[] expected1 = IM.getInterpolateNodes();
        double[] actual1 = new double[]{1, 2, 3, 4};

        assertArrayEquals(expected1, actual1);
    }

    @Test
    void getLagrangePolyn() {
        String path = "src\\test\\source\\testFillNodes.txt";
        InterpolMethods IM = new InterpolMethods();
        IM.fillFromFile(path);
        IM.setInterpolateValues("10*x");

        double expected = IM.getLagrangePolyn(2.5);
        double actual = 25;
        assertEquals(expected, actual);
    }

    @Test
    void calcValueAvailFunction() {
        String funk = "x^2";
        InterpolMethods IM = new InterpolMethods();
        IM.setTargetX(6);
        double expected = IM.calcValueAvailFunction(funk, IM.getTargetX());
        double actual = 36;
        assertEquals(expected, actual);
    }
}