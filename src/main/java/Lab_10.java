import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.Math.sin;
import java.util.Random;
import javax.swing.*;

public class Lab_10 extends JFrame {
    public static int numF = 0;
    public static double h = 0;
    public static double a = 0;
    public static double b = 0;
    public static int n = 0;
    public static double localH = 0;
    //Функция, которая считывает исходные данные
    public static void scan() {
        String s = "";
        try(BufferedReader br = new BufferedReader(new FileReader("src\\test\\source\\file_lab_10.txt")))
        {
            numF = Integer.parseInt(br.readLine());
            h = Double.parseDouble(br.readLine());
            localH = Double.parseDouble(br.readLine());
            n = Integer.parseInt(br.readLine());
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    //Находим решение
    public static double fun(double t, double xx, double yy) {
        if(numF == 1) {
            return 16 * pow(sin(t), 3) + yy - xx;
        }
        return 0;
    }
    //Вычисление x(t) в заданной точке
    public static double funX(double t) {
        if(numF == 1) {
            return 16 * pow(sin(t), 3);
        }
        if(numF == 2) {
            return t * sin(t);
        }
        return 0;
    }
    //Вычисление y(t) в заданной точке
    public static double funY(double t) {
        if(numF == 1) {
            return 13 * cos(t) - 5 * cos(2 * t) - 2 * cos(3 * t) - cos(4 * t);
        }
        if(numF == 2) {
            return t * cos(t);
        }
        return 0;
    }
    //Формируем таблицу значений
    public static void tabl(double t[], double x[], double y[]) {
        for(int i = 0; i < t.length; i++) {
            x[i] = funX(t[i]);
            y[i] = funY(t[i]);
        }
    }
    //Функция, формирующая матрицу для определения с
    public static void kC(double cA[][], double b[], double x[], double y[]) {
        for(int i = 1; i < cA.length - 1; i++) {
            cA[i][i - 1] = (x[i] - x[i - 1]) / 6;
            cA[i][i] = (-1) * ((x[i] - x[i - 1]) / 3 + (x[i + 1] - x[i]) / 3);
            cA[i][i + 1] = (x[i + 1] - x[i]) / 6;
            b[i] = ((y[i + 1] - y[i]) / (x[i + 1] - x[i])) - ((y[i] - y[i - 1]) / (x[i] - x[i - 1]));
        }
        cA[0][0] = (-1) * ((x[1] - x[0]) / 3 + (x[2] - x[1]) / 3);
        cA[0][1] = (x[2] - x[1]) / 6;
        b[0] = ((y[2] - y[1]) / (x[2] - x[1])) - ((y[1] - y[0]) / (x[1] - x[0]));
        cA[cA.length - 1][cA.length - 2] = (x[cA.length - 1] - x[cA.length - 2]) / 6;
        cA[cA.length - 1][cA.length - 1] = (-1) * ((x[cA.length] - x[cA.length - 1]) / 3 + (x[cA.length + 1] - x[cA.length]) / 3);
        b[cA.length - 1] = ((y[cA.length] - y[cA.length - 1]) / (x[cA.length] - x[cA.length - 1])) - ((y[cA.length - 1] - y[cA.length - 2]) / (x[cA.length - 1] - x[cA.length - 2]));

    }
    //Метод прогонки
    public static void progon(double cA[][], double masX[], double b[], double x[], double y[]) {
        double alfa[] = new double[cA.length + 1];
        double betta[] = new double[cA.length + 1];
        alfa[1] = cA[0][1]/cA[0][0];
        betta[1] = -b[0]/cA[0][0];
        for(int i = 2; i < cA.length; i++) {
            alfa[i] = cA[i - 1][i] / (cA[i - 1][i - 1] - cA[i - 1][i - 2] * alfa[i - 1]);
            betta[i] = (cA[i - 1][i - 2] * betta[i - 1] - b[i - 1]) / (cA[i - 1][i - 1] - cA[i - 1][i - 2] * alfa[i - 1]);
        }
        betta[cA.length] = (cA[cA.length - 1][cA.length - 2] * betta[cA.length - 1] - b[cA.length - 1]) / (cA[cA.length - 1][cA.length - 1] - cA[cA.length - 1][cA.length - 2] * alfa[cA.length - 1]);
        masX[cA.length - 1] = betta[cA.length];
        for(int i = cA.length - 2; i >= 0; i--) {
            masX[i] = alfa[i + 1] * masX[i + 1] + betta[i + 1];
        }
    }
    //Интерполирование кубическими сплайнами
    public static double Splan(double xx, double c[], int i, double x[], double y[]) {
        double pr = xx - x[i];
        double d = (c[i] - c[i - 1]) / (x[i] - x[i - 1]);
        double b = (y[i] - y[i - 1]) / (x[i] - x[i - 1]) + c[i] * ((x[i] - x[i - 1]) / 2) + d * (((x[i] - x[i - 1]) * (x[i] - x[i - 1])) / 6);
        return y[i] + b * pr + c[i] * pr * pr + (d / 6) * pr * pr * pr;
    }
    //Подынтегральная функция
    public static double subFun(double t) {
        if(numF == 1) {
            return (13 * cos(t) - 5 * cos(2 * t) - 2 * cos(3 * t) - cos(4 * t)) * (48 * sin(t) * sin(t) * cos(t));
        }
        if(numF == 2) {
            return t * cos(t) * (sin(t) + t * cos(t));
        }
        return 0;
    }
    //Составная формула Симпсона
    public static double funSim(double h) {
        double sp = 0;
        int count = (int)((b - a) / h) + 2;
        double x = a;
        double sum = 0;
        sum += subFun(x);
        x += h;
        sum += 4 * subFun(x);
        x += h;
        for(double i = 2; i < count - 1; i += 2) {
            sum += 2 * subFun(x);
            x += h;
            sum += 4 * subFun(x);
            x += h;
        }
        sum += subFun(x);
        sp = (h / 3) * sum;
        return sp;
    }
    //Метод Монте-Карло
    public static double monKar(int xToch[], int yToch[], int mach, int xI[], int yI[]) {
        Random rnd = new Random(System.currentTimeMillis());
        double gamma = 0;
        double xx[] = new double[n];
        double yy[] = new double[n];
        int a = -16;
        int b = 16;
        int c = -17;
        int d = 12;
        for(int i = 0; i < n; i++) {
            gamma = rnd.nextDouble();
            xx[i] = a + gamma * (b - a);
            gamma = rnd.nextDouble();
            yy[i] = c + gamma * (d - c);
            xToch[i] = (int) (round(xx[i] * mach));
            yToch[i] = (int) (round(yy[i] * mach));
            xToch[i] += 400;
            yToch[i] = 300 - yToch[i];
        }
        double sum = 0;
        for(int i = 0; i < n; i++) {
            int top = 0;
            int boot = 0;
            for(int j = 0; j < xI.length; j++) {
                if(xToch[i] == xI[j]) {
                    if(yToch[i] > yI[j]) {
                        top++;
                    }
                    else if(yToch[i] < yI[j]) {
                        boot++;
                    }
                    else if(yToch[i] == yI[j]) {
                        sum++;
                        break;
                    }
                }
            }
            if(top != 0 && boot != 0) {
                sum++;
            }
        }
        double k = (b - a) * (d - c);
        return (k / n) * sum;
    }
    //Функция, которая печатает результаты в файл
    public static void output(double sSim, double sMK, double sS) {
        try(FileWriter writer = new FileWriter("output.txt", false)) 
        {
            System.out.println("Заданная параметрическая функция:");
            if(numF == 1) {
                System.out.println("x(t) = 16sin^3(t)");
                System.out.println("y(t) = 13cos(t) - 5cos(2t) - 2cos(3t) - cos(4t)");
                System.out.println("t = [0; 2PI]");
                System.out.println("S по Симпсону = " + sSim);
            }
            if(numF == 2) {
                System.out.println("x(t) = t * sin(t)");
                System.out.println("y(t) = t * cos(t)");
                System.out.println("t = [0; 5PI]");
                System.out.println("S по Симпсону = " + sS);
            }
            System.out.println("S по Монте-Карло = " + sMK);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    } 
    public static void main(String[] args) {
        scan();
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Графики функций");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setVisible(true);
        Graphic g = new Graphic();
        frame.add(g);
        frame.setVisible(true);
        double sSim = 999999; 
        double sMK = 0;
        double sS = 0;
        if(numF == 1) {
            a = 0;
            b = 2 * PI;
        }
        if(numF == 2) {
            a = 0;
            b = 5 * PI;
        }
        int size = (int)((b - a) * (1 / h)) + 2;
        double t[] = new double[size];
        double x[] = new double[size];
        double y[] = new double[size];
        int xI[] = new int[(x.length - 1) * (int)(h / localH)];
        int yI[] = new int[(y.length - 1) * (int)(h / localH)];
        int i = 0;
        double z = a;
        while(z <= b) {
            t[i] = z;
            if(z == b) {
                break;
            }
            if(b - z < h) {
                z = b;
            }
            else {
                z += h;
            }
            i++;
        }
        tabl(t, x, y);
        double cAX[][] = new double[t.length - 2][t.length - 2];
        double bX[] = new double[t.length - 2];
        double cX[] = new double[t.length];
        double masX[] = new double[t.length - 2]; 
        kC(cAX, bX, t, x);
        progon(cAX, masX, bX, t, x);
        for(i = 0; i < masX.length; i++) {
            cX[i + 1] = masX[i];
        }
        double cAY[][] = new double[t.length - 2][t.length - 2];
        double bY[] = new double[t.length - 2];
        double cY[] = new double[t.length];
        double masY[] = new double[t.length - 2]; 
        kC(cAY, bY, t, y);
        progon(cAY, masY, bY, t, y);
        for(i = 0; i < masY.length; i++) {
            cY[i + 1] = masY[i];
        }
        int zz = 0;
        int mash = 10;
        for(i = 0; i < t.length; i++) {
            if(i != t.length - 1) {
                for(double j = t[i]; j + 0.0001 < t[i + 1]; j += localH) {
                    xI[zz] = (int) (round(Splan(j, cX, i + 1, t, x) * mash)) + 400;
                    yI[zz] = 300 - (int) (round(Splan(j, cY, i + 1, t, y) * mash));
                    zz++;
                }
            }
        }
        double e = 0.001;
        double hh = (b - a) / 4;
        double tochH = funSim(hh);
        while (abs(sSim - tochH) > e) {
            sSim = tochH;
            hh /= 2;
            tochH = funSim(hh);
        }
        sSim = tochH;
        if(numF == 2) {    
            sS = abs(sSim);
        }
        int xToch[] = new int[n];
        int yToch[] = new int[n];
        sMK = monKar(xToch, yToch, mash, xI, yI);
        g.graph(frame.getGraphics(), xI, yI, xToch, yToch);
        output(sSim, sMK, sS);
    }
}
