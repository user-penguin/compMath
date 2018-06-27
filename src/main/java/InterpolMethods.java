import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class InterpolMethods {

    private double[] iNodes;
    private double[] iValues;
    private double targetX;

    private String typeOfFunct;
    private double interpolateStep;
    private double localStep;
    private double leftBorder;
    private double rightBorder;

    InterpolMethods() {

    }

    InterpolMethods(String funct) {
        typeOfFunct = funct;
    }

    InterpolMethods(double[] tableX, double[] tableY) {
        iNodes = new double[tableX.length];
        iValues = new double[tableY.length];

        for (int i = 0; i < tableX.length; i++) {
            iNodes[i] = tableX[i];
            iValues[i] = tableY[i];
        }
    }

    public void setTypeOfFunct(String function) {
        typeOfFunct = function;
    }

    public double[] getiNodes() {
        return iNodes;
    }

    public double[] getiValues() {
        return iValues;
    }

    public double getTargetX() {
        return targetX;
    }

    // todo переделать в инпут
    public void setTargetX(double value) {
        targetX = value;
    }

    public void setTargetX() {
        System.out.println("Введите значение аргумента, в котором нужно вычислить приближённое значение: ");

        Scanner in = new Scanner(System.in);
        double target = in.nextDouble();

        targetX = target;
    }

    //todo пофиксить сет
    public void setiValues() {
        iValues = new double[iNodes.length];
        for (int i = 0; i < iNodes.length; i++)
            iValues[i] = calcValueAvailFunction(typeOfFunct, iNodes[i]);
    }

    public void fillFromFile(String path) {
        try {
            FileInputStream fstream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String read;

            read  = br.readLine();
            iNodes = splitToDoubleArray(read);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double[] getGraphicArg(int size) {
        double minArgument = iNodes[0];
        double maxArgument = iNodes[iNodes.length - 1];
        double step = Math.abs(maxArgument - minArgument) / size;
        double x = minArgument;
        double[] steps = new double[size];

        for (int i = 0; i < size; i++) {
            steps[i] = x;
            x += step;
        }

        return steps;
    }

    public double[] getNewtonValues(double[] arguments) {
        double[] newtonValues = new double[arguments.length];
        for (int i = 0; i < arguments.length; i++)
            newtonValues[i] = getNewtonPolyn(arguments[i]);

        return newtonValues;
    }

    public double[] getRealValues(double[] arguments, String function) {
        double[] values = new double[arguments.length];
        for (int i = 0; i < arguments.length; i++)
            values[i] = calcValueAvailFunction(function, arguments[i]);

        return  values;
    }

    private double[] splitToDoubleArray(String source) {
        double[] finalArray;
        String[] splittingNumbers;

        splittingNumbers = source.split(" ");
        finalArray = new double[splittingNumbers.length];

        for (int i = 0; i < splittingNumbers.length; i++)
            finalArray[i] = Double.parseDouble(splittingNumbers[i]);

        return finalArray;
    }

    public double calcValueAvailFunction(String function, double argument) {
        switch (function) {
            case "x^2":
                return argument * argument;

            case "sin(x)":
                return Math.sin(argument);

            case "cos(x)":
                return Math.cos(argument);

            case "x^3":
                return argument * argument * argument;

            case "|x|":
                return Math.abs(argument);

            case "10*x":
                return argument * 10;

            default:
                throw new UnsupportedOperationException();
        }
    }

    public double getDividedDiff(int n) {
        double sum = 0;
        for (int i = 0; i < n; i++) {
            double multip = 1;
            for (int l = 0; l < n; l++) {
                if (i != l) {
                    multip *= iNodes[i] - iNodes[l];
                }
            }
            sum += iValues[i] / multip;
        }
        return sum;
    }

    public double getLagrangePolyn(double targetX){
        double sum = 0;
        for(int k = 0; k < iNodes.length; k++) {
            double multipUp = 1;
            double multipDown = 1;
            for(int j = 0; j < iNodes.length; j++) {
                if(j != k) {
                    multipUp *= (targetX - iNodes[j]);
                    multipDown *= (iNodes[k] - iNodes[j]);
                }
            }
            sum += (multipUp * iValues[k]) / multipDown;
        }

        return sum;
    }

    public double getNewtonPolyn(double targetX) {
        double multip = 1;
        double sum = 0;
        for (int i = 0; i < iNodes.length; i++) {
            sum += getDividedDiff(i + 1) * multip;
            multip *= (targetX - iNodes[i]);
        }
        return sum;
    }

}
