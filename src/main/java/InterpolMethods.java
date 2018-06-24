import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class InterpolMethods {

    private double[] interpolateNodes;
    private double[] interpolateValues;
    private double targetX;

    public double[] getNodes() {
        return interpolateNodes;
    }

    public double[] getInterpolateValues() {
        return interpolateValues;
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

            read = br.readLine();
            interpolateValues = splitToDoubleArray(read);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
//
//    public double calcValueAvailFunction(int number) {
//        switch (number) {
//            case 1:
//                return targetX * targetX;
//        }
//    }


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
