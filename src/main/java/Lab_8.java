import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import static java.lang.Math.*;

public class Lab_8 extends Lab {

    public static void main(String[] arg) {
        Lab_8 lab_8 = new Lab_8();
        lab_8.fillFromFile("src\\test\\source\\file_lab_8.txt");
        lab_8.run();
        lab_8.print();
    }

    private int numberFunction;
    private double step;
    private double level = 0.05;
    private double[] x;
    private double[] y;

    double[] firstDerivative;
    double[] realFirstDerivative;
    double[] inaccuracyFirstDerivative;

    double[] secondDerivative;
    double[] realSecondDerivative;
    double[] inaccuracySecondDerivative;

    double[] thirdDerivative;
    double[] realThirdDerivative;
    double[] inaccuracyThirdDerivative;

    @Override
    public void fillFromFile(String path) {
        try(BufferedReader br = new BufferedReader(new FileReader(path)))
        {
            numberFunction = Integer.parseInt(br.readLine());

            int a = Integer.parseInt(br.readLine());
            int b = Integer.parseInt(br.readLine());

            step = Double.parseDouble(br.readLine());

            level = Double.parseDouble(br.readLine());

            int countStep = (int)((b - a) * (1 / step)) + 1;
            x = new double[countStep];
            y = new double[countStep];

            for (int i = 0; a + i * step <= b; i++)
                x[i] = a + i * step;
            calcY();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void run() {
        vozm();
        firstDerivative = calcFirstDerivative();
        realFirstDerivative = calcRealFirstDerivative();
        inaccuracyFirstDerivative = MatrixMath.calcInaccuracy(firstDerivative, realFirstDerivative);

        secondDerivative = calcSecondDerivative();
        realSecondDerivative = calcRealSecondDerivative();
        inaccuracySecondDerivative = MatrixMath.calcInaccuracy(secondDerivative, realSecondDerivative);

        thirdDerivative = calcThirdDerivative();
        realThirdDerivative = calcRealThirdDerivative();
        inaccuracyThirdDerivative = MatrixMath.calcInaccuracy(thirdDerivative, realThirdDerivative);
    }

    @Override
    public void print() {
        try(FileWriter writer = new FileWriter("output.txt", false))
        {
            String str = "Заданная функция: " + getFunctionString(numberFunction) + "\n";
            str += "Таблица производных:\n";
            System.out.println(str);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        String format = "|";
        for (int j = 0; j < 11; j++)
            format += " %12f |";
        format += "\n";

        String formatStr = "|";
        for (int j = 0; j < 11; j++)
            formatStr += " %12s |";
        formatStr += "\n";

        System.out.printf(formatStr, "x", "y",
                "y'", "real y'", "inaccuracy",
                "y''", "real y''", "inaccuracy",
                "y'''", "real y'''", "inaccuracy");
        for (int i = 0; i < x.length; i++) {
            System.out.printf(format,
                    x[i],
                    y[i],
                    firstDerivative[i],
                    realFirstDerivative[i],
                    inaccuracyFirstDerivative[i],
                    secondDerivative[i],
                    realSecondDerivative[i],
                    inaccuracySecondDerivative[i],
                    thirdDerivative[i],
                    realThirdDerivative[i],
                    inaccuracyThirdDerivative[i]);
        }
    }

    private String getFunctionString(int numberFunction) {
        String f = "";
        if(numberFunction == 1)
            f = "x^4 - 7x^2 + 4x";
        if(numberFunction == 2)
            f = "(x - 5)cos x";
        if(numberFunction == 3)
            f = "|x|";
        if(numberFunction == 4)
            f = "e^(-2x^2)";
        if(numberFunction == 5)
            f = "sin x";
        return f;
    }

    private double calcY(double x) {
        if(numberFunction == 1)
            return x * x * x * x - 7 * x * x + 4 * x;
        if(numberFunction == 2)
            return (x - 5) * cos(x);
        if(numberFunction == 3)
            return abs(x);
        if(numberFunction == 4)
            return pow(Math.E, -2 * x * x);
        if(numberFunction == 5)
            return sin(x);
        return 0;
    }

    private void calcY() {
        for(int i = 0; i < y.length; i++)
            y[i] = calcY(x[i]);
    }

    private double calcRealFirstDerivative(double x) {
        if(numberFunction == 1)
            return 4 * x * x * x - 14 * x + 4;
        if(numberFunction == 2)
            return (x - 5) * cos(x);
        if(numberFunction == 3)
            return abs(x);
        if(numberFunction == 4)
            return pow(Math.E, -2 * x * x);
        if(numberFunction == 5)
            return sin(x);
        return 0;
    }

    private double[] calcRealFirstDerivative() {
        double[] realDerivative = new double[x.length];
        for(int i = 0; i < x.length - 1; i++)
            realDerivative[i] = calcRealFirstDerivative(x[i]);
        return realDerivative;
    }

    private double[] calcFirstDerivative() {
        double[] derivative = new double[x.length];
        derivative[0] = (-3 * y[0] + 4 * y[1] - y[2]) / (2 * step);
        for(int i = 1; i < x.length - 1; i++)
            derivative[i] = (y[i + 1] - y[i - 1]) / (2 * step);
        //firstY[x.length - 1] = (3 * y[x.length - 1] - 4 * y[x.length - 2] + y[x.length - 3]) / (2 * h);
        return derivative;
    }

    private double calcRealSecondDerivative(double x) {
        if(numberFunction == 1)
            return 12 * x * x - 14;
        if(numberFunction == 2)
            return (x - 5) * cos(x);
        if(numberFunction == 3)
            return abs(x);
        if(numberFunction == 4)
            return pow(Math.E, -2 * x * x);
        if(numberFunction == 5)
            return sin(x);
        return 0;
    }

    private double[] calcRealSecondDerivative() {
        double[] realDerivative = new double[x.length];
        for(int i = 0; i < x.length - 1; i++)
            realDerivative[i] = calcRealSecondDerivative(x[i]);
        return realDerivative;
    }

    private double[] calcSecondDerivative() {
        double[] derivative = new double[x.length];
        derivative[0] = (5 * y[0] - 11 * y[1] + 7 * y[2] - y[3]) / (4 * step * step);
        for(int i = 1; i < x.length - 1; i++) {
            derivative[i] = (y[i - 1] - 2 * y[i] + y[i + 1]) / (step * step);
        }
        //derivative[x.length - 1] = (5 * y[x.length - 1] - 11 * y[x.length - 2] + 7 * y[x.length - 3] - y[x.length - 4]) / (4 * step * step);
        return derivative;
    }

    private double calcRealThirdDerivative(double x) {
        if(numberFunction == 1)
            return 24 * x;
        if(numberFunction == 2)
            return (x - 5) * cos(x);
        if(numberFunction == 3)
            return abs(x);
        if(numberFunction == 4)
            return pow(Math.E, -2 * x * x);
        if(numberFunction == 5)
            return sin(x);
        return 0;
    }

    private double[] calcRealThirdDerivative() {
        double[] realDerivative = new double[x.length];
        for(int i = 0; i < x.length - 1; i++)
            realDerivative[i] = calcRealThirdDerivative(x[i]);
        return realDerivative;
    }

    private double[] calcThirdDerivative() {
        double[] derivative = new double[x.length];
        derivative[0] = (-4 * y[0] + 13 * y[1] - 15 * y[2] + 7 * y[3] - y[4]) / (8 * step * step * step);
        derivative[1] = (-4 * y[1] + 13 * y[2] - 15 * y[3] + 7 * y[4] - y[5]) / (8 * step * step * step);
        for(int i = 2; i < x.length - 2; i++) {
            derivative[i] = (y[i + 2] - 2 * y[i + 1] + 2 * y[i - 1] - y[i - 2]) / (2 * step * step * step);
        }
        //derivative[x.length - 2] = (4 * y[x.length - 2] - 13 * y[x.length - 3] + 15 * y[x.length - 4] - 7 * y[x.length - 5] + y[x.length - 6]) / (8 * step * step * step);
        //derivative[x.length - 1] = (4 * y[x.length - 1] - 13 * y[x.length - 2] + 15 * y[x.length - 3] - 7 * y[x.length - 4] + y[x.length - 5]) / (8 * step * step * step);
        return derivative;
    }

    public void vozm() {
        Random rnd = new Random(System.currentTimeMillis());
        double vozm = 0;
        for(int i = 0; i < y.length; i++) {
            while((vozm = rnd.nextDouble()) > level) {}
            y[i] += vozm;
        }
    }

}
