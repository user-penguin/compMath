import java.util.Scanner;

/**
 * ИНТЕРПОЛИРОВАНИЕ
 */

public class Lab_6 {

    // constructors

    public Lab_6(String path) {
        Scanner in = new Scanner(System.in);
        String function;
        System.out.println("Введите название функции, с которой будем работать: ");
        function = in.next();

        InterpolMethods IM = new InterpolMethods(function);
        IM.fillFromFile(path);



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
