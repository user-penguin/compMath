import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static java.lang.Math.abs;

/**
 * полная проблема собственных чисел
 * только для симметричных матриц
 * https://lektsii.org/13-70062.html
 */

public class Lab_5 extends Lab {

    private double[][] A;
    private double[][] start;
    private double[]personalNumbers;
    private double[] sumA;
    private UnitMatrix e; // мать собственных векторов
    private Matrix nevjazki;



    public static void main(String[] arg) {
        Lab_5 lab_5 = new Lab_5();
        lab_5.fillFromFile("src\\test\\source\\file_lab_5.txt");
        lab_5.run();
        lab_5.print();

    }

    private void addOneRow(String readRow, ArrayList<double[]> matrix) {
        String[] toSplit = readRow.split(" ");
        int width = toSplit.length;
        double oneString[] = new double[width];
        for(int i = 0; i < width; i++)
            oneString[i] = Double.parseDouble(toSplit[i]);
        matrix.add(oneString);
    }

    private double[][] toArray(ArrayList<double[]> matrixArr) {
        double[][] matrix = new double[matrixArr.size()][matrixArr.get(0).length];
        for(int i = 0; i < matrixArr.size(); i++)
            for(int j = 0; j < matrixArr.size(); j++)
                matrix[i][j] = matrixArr.get(i)[j];
        return matrix;
    }

    @Override
    public void fillFromFile(String path) {
        ArrayList<double[]> matrix = new ArrayList<>();
        try {
            FileInputStream fstream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String read;

            while((read = br.readLine()) != null)
                addOneRow(read, matrix);
            this.A = toArray(matrix);
            this.start = new double[this.A.length][this.A.length];
            for (int i = 0; i < this.A.length; i++)
                for (int j = 0; j < this.A.length; j++)
                    this.start[i][j] = this.A[i][j];
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        sumA = calculateSumA(this.A);
        e = new UnitMatrix(A.length);
        int[] indexes = new int[2];

        while (!(searchMaxElem(A, indexes, sumA) < MatrixMath.E)) {
            Matrix U = new Matrix(uklMatrix(A, indexes[0], indexes[1]));

            // A = UT * A * U

            A = new Matrix(MatrixMath.multip(U.transpose(), A)).getMatrix();
            A = new Matrix(MatrixMath.multip(A, U.getMatrix())).getMatrix();

            // e = e * U
            e = new UnitMatrix(MatrixMath.multip(e.getMatrix(), U.getMatrix()));

            sumA = calculateSumA(A);
        }

        personalNumbers = new double[A.length];
        for (int i = 0; i < personalNumbers.length; i++)
            personalNumbers[i] = A[i][i];

        normalization(e.getMatrix());

        nevjazki = new Matrix(new double[A.length][A.length]);
        nevjazka(this.start, personalNumbers, e.getMatrix(), nevjazki.getMatrix());

    }

    @Override
    public void print() {
        Matrix startMatrix = new Matrix(this.start);
        startMatrix.print("Исходная матрица A: ");
        for (int i = 0; i < personalNumbers.length; i++) {
            System.out.println("\nСобственное число №" + (i + 1) + ": " + personalNumbers[i]);
            System.out.print("Собственный вектор:    (");
            for (int j = 0; j < personalNumbers.length; j++)
                System.out.print(e.getMatrix()[j][i] + " ");
            System.out.println(")");
            System.out.print("Невязка: ");
            for (int j = 0; j < personalNumbers.length; j++)
                System.out.print(nevjazki.getMatrix()[i][j] + " ");
            System.out.println();
        }
    }

    // private

    private static void print(double[] personalNumbers, Matrix e, Matrix nevjazki, Matrix start) {
        start.print("Исходная матрица A: ");
        for (int i = 0; i < personalNumbers.length; i++) {
            System.out.println("\nСобственное число №" + (i + 1) + ": " + personalNumbers[i]);
            System.out.print("Собственный вектор:    (");
            for (int j = 0; j < personalNumbers.length; j++)
                System.out.print(e.getMatrix()[j][i] + " ");
            System.out.println(")");
            System.out.print("Невязка: ");
            for (int j = 0; j < personalNumbers.length; j++)
                System.out.print(nevjazki.getMatrix()[i][j] + " ");
            System.out.println();
        }
    }

    private static double calculateMu(double[][] matrix, int k, int l){
        return (2 * matrix[k][l]) / (matrix[k][k] - matrix[l][l]);
    }

    private static double calculateAlpha(double[][] matrix, int k, int l) {
        if (matrix[k][k] == matrix[l][l])
            return Math.sqrt(0.5);
        else {
            double mu = calculateMu(matrix, k, l);
            return Math.sqrt((1 + 1 / (Math.sqrt(1 + mu * mu))) / 2);
        }
    }

    private static double calculateBetta(double[][] matrix, int k, int l) {
        if (matrix[k][k] == matrix[l][l])
            return Math.sqrt(0.5);
        else {
            double mu = calculateMu(matrix, k, l);
            return sign(mu) * Math.sqrt((1 - 1 / (Math.sqrt(1 + mu * mu))) / 2);
        }
    }

    private static double[][] uklMatrix(double[][] A, int k, int l) {
        int size = A.length;
        double[][] matrix = new double[size][size];

        for(int i = 0; i < size; i++)
            matrix[i][i] = 1;

        matrix[k][k] = matrix[l][l] = calculateAlpha(A, k, l);
        matrix[k][l] = -1 * calculateBetta(A, k, l);
        matrix[l][k] = calculateBetta(A, k, l);

        return matrix;
    }

    private static double searchMaxElem(double[][] A, int[] indexes, double[] sumA) {
        double max = 0;
        for(int i = 0; i < A.length; i++)
            if(sumA[i] > max) {
                max = sumA[i];
                indexes[0] = i;
            }

        max = 0;
        for(int i = 0; i < A.length; i++)
            if((Math.abs(A[indexes[0]][i]) > Math.abs(max)) && (indexes[0] != i)) {
                max = A[indexes[0]][i];
                indexes[1] = i;
            }

        if(indexes[0] > indexes[1]){
            int a = indexes[0];
            indexes[0] = indexes[1];
            indexes[1] = a;
        }

        return Math.abs(max);
    }

    private static double[] calculateSumA(double[][] A) {
        double[] sumA = new double[A.length];
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A.length; j++)
                if (i != j)
                    sumA[i] += A[i][j] * A[i][j];
        return sumA;
    }

    private static int sign(double d) {
        if(d < 0)
            return -1;
        else
            return 1;
    }

    private static void normalization(double[][] matrix) {
        int n = matrix.length;
        for(int i = 0; i < n; i++) {
            double norm = 0;
            int k = n - 1;
            while(matrix[k][i] == 0)
                k--;

            for(int j = 0; j < n; j++)
                matrix[j][i] /= matrix[k][i];
        }
    }

    private static void nevjazka(double A[][], double lambda[], double e[][], double nevjazki[][]) {
        int n = A.length;
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++) {
                double sum = 0;
                for(int z = 0; z < n; z++)
                    sum += A[j][z] * e[z][i];
                nevjazki[i][j] = lambda[i] * e[j][i] - sum;
            }
    }
}
