import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterpolMethodsTest {


    @Test
    void fillFromFile() {
        String path = "src\\test\\source\\testFillNodes.txt";
        InterpolMethods IM = new InterpolMethods();
        IM.fillFromFile(path);
        double[] expected1 = IM.getiNodes();
        double[] actual1 = new double[]{1, 2, 3, 4};

        assertArrayEquals(expected1, actual1);
    }

    @Test
    void getLagrangePolyn() {
        String path = "src\\test\\source\\testFillNodes.txt";
        InterpolMethods IM = new InterpolMethods();
        IM.fillFromFile(path);
        IM.setTypeOfFunct("10*x");
        IM.setiValues();

        double expected = IM.getLagrangePolyn(2.5);
        double actual = 25;
        assertEquals(expected, actual);
    }

    @Test
    void calcValueAvailFunction() {
        String funk = "sin(x)";
        InterpolMethods IM = new InterpolMethods();
        IM.setTargetX(0);
        double expected = IM.calcValueAvailFunction(funk, IM.getTargetX());
        double actual = 0;
        assertEquals(expected, actual);
    }
}