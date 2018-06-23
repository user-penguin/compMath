import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class InterpolMethods {

    private static double[] interpolateNodes;
    private static double[] interpolateValues;

    public static void fillFromFile(String path) {
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

    private static double[] splitToDoubleArray(String source) {
        double[] finalArray;
        String[] splittingNumbers;

        splittingNumbers = source.split(" ");
        finalArray = new double[splittingNumbers.length];

        for (int i = 0; i < splittingNumbers.length; i++)
            finalArray[i] = Double.parseDouble(splittingNumbers[i]);

        return finalArray;
    }
//todo
//    public static void getLagrangePolyn(double sourceX){
//        double sum = 0;
//        for(int k = 0; k < .length; k++) {
//            double pr1 = 1;
//            double pr2 = 1;
//            for(int j = 0; j < x.length; j++) {
//                if(j != k) {
//                    pr1 *= (xx - x[j]);
//                    pr2 *= (x[k] - x[j]);
//                }
//            }
//            sum += (pr1 * y[k]) / pr2;
//        }
//        return sum;
//    }
}
