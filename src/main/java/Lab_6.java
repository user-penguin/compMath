import java.util.Scanner;


/**
 * ИНТЕРПОЛИРОВАНИЕ
 */

public class Lab_6 {

    public static void main(String[] arg) {
        Lab_6 lab_6 = new Lab_6("src\\test\\source\\Lab6_in.txt");
    }

    // constructors

    public Lab_6(String path) {
        Scanner in = new Scanner(System.in);
        InterpolMethods IM = new InterpolMethods();
        IM.fillFromFile(path);

        String function;
        System.out.println("Введите название функции, с которой будем работать: ");
        function = in.next();
        IM.setInterpolateValues(function);

        IM.setTargetX();

        System.out.println("Значение в точке " + IM.getTargetX() + " по Лагранжу: "
                + IM.getLagrangePolyn(IM.getTargetX()));
        System.out.println("Значение в точке " + IM.getTargetX() + " по Ньютону: "
                + IM.getNewtonPolyn(IM.getTargetX()));

        double[] arguments = IM.getGraphicArg(1000);
        double[] newtonValues = IM.getNewtonValues(arguments);
        double[] realValues = IM.getRealValues(arguments, function);

        JFreeChartTools graphic = new JFreeChartTools(function, arguments, newtonValues, realValues);
        graphic.pack();
        graphic.setVisible(true);
    }

}
