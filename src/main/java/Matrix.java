import java.io.*;
import java.util.ArrayList;

import static java.lang.Math.sqrt;

public class Matrix {

    private double[][] matrix;

    public Matrix() {}

    public Matrix(double[][] matrix) {
        this.matrix = matrix;
    }

    private void addOneRow(String read, ArrayList<double[]> matrix) {
        String[] toSplit = read.split(" ");
        int width = toSplit.length;
        double oneString[] = new double[width];
        for(int i = 0; i < width; i++)
            oneString[i] = Double.parseDouble(toSplit[i]);
        matrix.add(oneString);
    }

    public void fillFromFile(String path) {
        ArrayList<double[]> matrix = new ArrayList<>();
        try {
            FileInputStream fstream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String read;

            while((read = br.readLine()) != null)
                addOneRow(read, matrix);

            this.matrix = toArray(matrix);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print() {
        print(this.matrix);
    }

    public void print(double[] vector){
        for (double i : vector)
            System.out.println(i);
    }

    public void print(double[][] matrix) {
        for (double[] row : matrix) {
            for (double i : row)
                System.out.print(i + " ");
            System.out.println();
        }
    }

    public double[][] toArray(ArrayList<double[]> matrixArr) {
        double[][] matrix = new double[matrixArr.size()][matrixArr.get(0).length];
        for(int i = 0; i < matrixArr.size(); i++)
            System.arraycopy (matrixArr, 0, matrix, 0, matrix.length);
        return matrix;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public double calculateAlpha(int k, int l) {
        if (matrix[k][k] == matrix[l][l])
            return Math.sqrt(0.5);
        else {
            double mu = calculateMu(k, l);
            return Math.sqrt((1 + 1 / (Math.sqrt(1 + mu * mu))) / 2);
        }
    }

    public double calculateBetta(int k, int l) {
        if (matrix[k][k] == matrix[l][l])
            return Math.sqrt(0.5);
        else {
            double mu = calculateMu(k, l);
            return Math.signum(mu) * Math.sqrt((1 - 1 / (Math.sqrt(1 + mu * mu))) / 2);
        }
    }

    private double calculateMu(int k, int l){
        return (2 * matrix[k][l]) / (matrix[k][k] - matrix[l][l]);
    }
}
