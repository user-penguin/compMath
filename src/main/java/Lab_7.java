import javax.swing.*;
import java.awt.*;

/**
 * Интеpполиpование сплайнами
 */

public class Lab_7 {

    public static void main(String[] arg) {
        Lab_7 lab_7 = new Lab_7("input.txt");
    }

    // constructors

    public Lab_7(String path) {
        Graphic graphic = new Graphic();
        graphic.addLine(new int[]{0, 20, 30}, new int[]{0, 20, 70});
        graphic.paint();
    }

}
