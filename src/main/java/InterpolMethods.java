import jdk.internal.org.objectweb.asm.tree.analysis.Interpreter;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class InterpolMethods {

    private double[] interpolateNodes;
    private double[] interpolateValues;
    private double targetX;

    public double[] getInterpolateNodes() {
        return interpolateNodes;
    }

    public double[] getInterpolateValues() {
        return interpolateValues;
    }

    public double getTargetX() {
        return targetX;
    }

    public void setInterpolateValues(String function) {
        interpolateValues = new double[interpolateNodes.length];
        for (int i = 0; i < interpolateNodes.length; i++)
            interpolateValues[i] = calcValueAvailFunction(function, interpolateNodes[i]);
    }

    public void setTargetX(double value) {
        targetX = value;
    }

    public void setTargetX() {
        System.out.println("Введите значение аргумента, в котором нужно вычислить приближённое значение: ");

        Scanner in = new Scanner(System.in);
        double target = in.nextDouble();

        targetX = target;
    }

    public void fillFromFile(String path) {
        try {
            FileInputStream fstream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String read;

            read  = br.readLine();
            interpolateNodes = splitToDoubleArray(read);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double[] getGraphicArg(int size) {
        double minArgument = interpolateNodes[0];
        double maxArgument = interpolateNodes[interpolateNodes.length - 1];
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
                    multip *= interpolateNodes[i] - interpolateNodes[l];
                }
            }
            sum += interpolateValues[i] / multip;
        }
        return sum;
    }

    public double getLagrangePolyn(double targetX){
        double sum = 0;
        for(int k = 0; k < interpolateNodes.length; k++) {
            double multipUp = 1;
            double multipDown = 1;
            for(int j = 0; j < interpolateNodes.length; j++) {
                if(j != k) {
                    multipUp *= (targetX - interpolateNodes[j]);
                    multipDown *= (interpolateNodes[k] - interpolateNodes[j]);
                }
            }
            sum += (multipUp * interpolateValues[k]) / multipDown;
        }

        return sum;
    }

    public double getNewtonPolyn(double targetX) {
        double multip = 1;
        double sum = 0;
        for (int i = 0; i < interpolateNodes.length; i++) {
            sum += getDividedDiff(i + 1) * multip;
            multip *= (targetX - interpolateNodes[i]);
        }
        return sum;
    }
}
