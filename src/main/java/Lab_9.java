/**
 * ПРОСТЕЙШИЕ КВАДРАТУРНЫЕ ФОРМУЛЫ
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import static java.lang.Math.*;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Lab_9 extends Lab {

    private int reg; // метод интегрирования

    private double step;
    private int numberFunction;
    private double a; // начало отрезка
    private double b; // конец отрезка
    private double e; // точность
    private int[] countDivStep = new int[5];

    enum TypeIntegral {Left, Middle, Right}

    double leftRectangleIntegral;
    double middleRectangleIntegral;
    double rightRectangleIntegral;
    double trapezeIntegral;
    double simpsonIntegral;

    public static void main(String[] arg) {
        Lab_9 lab_9 = new Lab_9();
        lab_9.fillFromFile("src\\test\\source\\file_lab_9.txt");
        lab_9.run();
        lab_9.print();
    }

    @Override
    public void fillFromFile(String path) {
        try(BufferedReader br = new BufferedReader(new FileReader("src\\test\\source\\file_lab_9.txt")))
        {
            reg = Integer.parseInt(br.readLine());
            step = Double.parseDouble(br.readLine());
            numberFunction = Integer.parseInt(br.readLine());
            a = Integer.parseInt(br.readLine());
            b = Integer.parseInt(br.readLine());
            e = Double.parseDouble(br.readLine());
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void run() {
        if(reg == 1) {
            leftRectangleIntegral = calcIntegral(TypeIntegral.Left);
            middleRectangleIntegral = calcIntegral(TypeIntegral.Middle);
            rightRectangleIntegral = calcIntegral(TypeIntegral.Right);
            trapezeIntegral = calcIntegralTrapeze();
            simpsonIntegral = calcIntegralSimpson();
        }

        if (reg == 2) {

            int i = 0;
            TypeIntegral typeIntegral = TypeIntegral.Left;
            step = b - a;
            double integral = calcIntegral(typeIntegral);
            while (Math.abs(leftRectangleIntegral - integral) > e) {
                countDivStep[i]++;
                leftRectangleIntegral = integral;
                step /= 2;
                integral = calcIntegral(typeIntegral);
            }
            leftRectangleIntegral = integral;


            i = 1;
            typeIntegral = TypeIntegral.Middle;
            step = b - a;
            integral = calcIntegral(typeIntegral);
            while (Math.abs(middleRectangleIntegral - integral) > e) {
                countDivStep[i]++;
                middleRectangleIntegral = integral;
                step /= 2;
                integral = calcIntegral(typeIntegral);
            }
            middleRectangleIntegral = integral;


            i = 2;
            typeIntegral = TypeIntegral.Right;
            step = b - a;
            integral = calcIntegral(typeIntegral);
            while (Math.abs(rightRectangleIntegral - integral) > e) {
                countDivStep[i]++;
                rightRectangleIntegral = integral;
                step /= 2;
                integral = calcIntegral(typeIntegral);
            }
            rightRectangleIntegral = integral;


            i = 3;
            step = b - a;
            integral = calcIntegralTrapeze();
            while (Math.abs(trapezeIntegral - integral) > e) {
                countDivStep[i]++;
                trapezeIntegral = integral;
                step /= 2;
                integral = calcIntegralTrapeze();
            }
            trapezeIntegral = integral;


            i = 4;
            step = b - a;
            integral = calcIntegralSimpson();
            while (Math.abs(simpsonIntegral - integral) > e) {
                countDivStep[i]++;
                simpsonIntegral = integral;
                step /= 2;
                integral = calcIntegralSimpson();
            }
            simpsonIntegral = integral;
        }
    }

    @Override
    public void print() {
        String f = getFunctionString(numberFunction);
        System.out.println("Заданная функция: " + f);
        System.out.println("Отрезок:  [" +  a + "; " + b + "]");

        if(reg == 1) {
            System.out.println("Режим - с постоянным шагом интегрирования.");
            System.out.println("Шаг = " + step);
        }
        if(reg == 2) {
            System.out.println("Режим - с автоматическим шагом интегрирования.");
            System.out.println("Точность = " + e);
        }


        System.out.println("Значение интеграла," +
                " вычисленный по формуле левых прямоугольников = " + leftRectangleIntegral);
        if(reg == 2)
            System.out.println("Количество шагов = " + countDivStep[0]);

        System.out.println("Значение интеграла," +
                " вычисленный по формуле средних прямоугольников = " + middleRectangleIntegral);
        if(reg == 2)
            System.out.println("Количество шагов = " + countDivStep[1]);

        System.out.println("Значение интеграла," +
                " вычисленный по формуле правых прямоугольников = " + rightRectangleIntegral);
        if(reg == 2)
            System.out.println("Количество шагов = " + countDivStep[2]);

        System.out.println("Значение интеграла, " +
                "вычисленный по формуле трапеций = " + trapezeIntegral);
        if(reg == 2)
            System.out.println("Количество шагов = " + countDivStep[3]);

        System.out.println("Значение интеграла," +
                " вычисленный по формуле Симпсона = " + simpsonIntegral);
        if(reg == 2)
            System.out.println("Количество шагов = " + countDivStep[4]);
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
        if(numberFunction == 6)
            return sin(x) / x;
        if(numberFunction == 7)
            return cos(x) / x;
        return 0;
    }

    private double calcStartInteg(TypeIntegral typeIntegral) {
        double x = 0;
        if (typeIntegral == TypeIntegral.Left)
            x = a;
        if (typeIntegral == TypeIntegral.Middle)
            x = a + (step / 2);
        if(typeIntegral == TypeIntegral.Right)
            x = a + step;
        return x;
    }

    private double calcIntegral(TypeIntegral typeIntegral) {
        double integral = 0;
        int countStep = (int)((b - a) / step) + 1;
        double x = calcStartInteg(typeIntegral);
        for (int i = 0; i < countStep - 1; i++) {
            integral += calcY(x) * step;
            x += step;
        }
        return integral;
    }

    private double calcIntegralTrapeze() {
        double integral;
        int countStep = (int)((b - a) / step) + 1;
        double sum = calcY(a) / 2;
        double x = a + step;
        for (int i = 1; i < countStep - 1; i++) {
            sum += calcY(x);
            x += step;
        }
        sum += calcY(x) / 2;
        integral = step * sum;
        return integral;
    }

    private double calcIntegralSimpson() {
        double integral;
        int countStep = (int)((b - a) / step) + 1;

        double sum = calcY(a);
        double x = a + step;
        sum += 4 * calcY(x);
        x += step;

        for(double i = 2; i < countStep - 1; i ++) {
            sum += 2 * calcY(x) * (i % 2 + 1);
            x += step;
        }

        sum += calcY(x);
        integral = (step / 3) * sum;
        return integral;
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
        if(numberFunction == 6)
            f = "(sin x) / x";
        if(numberFunction == 7)
            f = "(cos x) / x";
        return f;
    }
}
