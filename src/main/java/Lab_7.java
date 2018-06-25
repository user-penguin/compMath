/**
 * Интеpполиpование сплайнами
 */

public class Lab_7 {

    public static void main(String[] arg) {
        Lab_7 lab_7 = new Lab_7("src\\test\\source\\file_lab_7.txt");
    }

    // constructors

    public Lab_7(String path) {
        InterpolMethods IM = new InterpolMethods();
        IM.fillSplineData(path);
        IM.calcSplineArguments();


    }

}
