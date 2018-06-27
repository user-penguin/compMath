import java.util.ArrayList;

/**
 * Интеpполиpование сплайнами
 */

public class Lab_7 extends Lab{

    private SplineMethods SM;
    private double[][] tridiagonalMatrix;
    private double E = 0.0001;


    public double[] freeB;


    public static void main(String[] args) {
        Lab_7 lab_7 = new Lab_7();
        lab_7.fillFromFile("src\\test\\source\\file_lab_7.txt");
        lab_7.run();
        lab_7.print();
    }

    @Override
    public void fillFromFile(String path) {
        SM = new SplineMethods("sin(x)");
        SM.fillSplineData(path);
    }

    @Override
    public void run() {
        SM.calcSplineArguments();
        SM.calcSplineValues();

        tridiagonalMatrix = SM.getTridiagonalMatrix();
        SM.tridiagonalMethod();
        SM.setIndexD();
        SM.setIndexB();

        // рисованиие
        double tableStep = SM.getInterpolateStep();
        double iterateStep = SM.getLocalStep();
        int size = (SM.getiNodes().length - 1) * (int) (tableStep / iterateStep);
        InterpolMethods IM = new InterpolMethods(SM.getiNodes(), SM.getiValues());
        IM.setTypeOfFunct(SM.getTypeOfFunct());

        ArrayList<Double> graphArgumentsList = new ArrayList<>();
        ArrayList<Double> graphSplineValuesList = new ArrayList<>();
        ArrayList<Double> graphNewtonValuesList = new ArrayList<>();
        ArrayList<Double> graphRealValuesList = new ArrayList<>();

        for (int i = 0; i < SM.getiNodes().length - 1; i++) {
            for (double j = SM.getiNodes()[i]; j + E < SM.getiNodes()[i+1]; j += iterateStep) {
                System.out.println(j);
                graphArgumentsList.add(j);
                graphSplineValuesList.add(SM.getSpline(j, i));
                graphNewtonValuesList.add(IM.getNewtonPolyn(j));
                graphRealValuesList.add(IM.calcValueAvailFunction(SM.getTypeOfFunct(), j));
            }
        }

        double[] graphArgumentsArr = SM.transformListToArray(graphArgumentsList);
        double[] graphSplineValuesArr = SM.transformListToArray(graphSplineValuesList);
        double[] graphNewtonValuesArr = SM.transformListToArray(graphNewtonValuesList);
        double[] graphRealValuesArr = SM.transformListToArray(graphRealValuesList);


        JFreeChartTools graphic = new JFreeChartTools(SM.getTypeOfFunct(), graphArgumentsArr, graphSplineValuesArr,
                graphNewtonValuesArr, graphRealValuesArr);
        graphic.pack();
        graphic.setVisible(true);

    }

    @Override
    public void print() {}

}
