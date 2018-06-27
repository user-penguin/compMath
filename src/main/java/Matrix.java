import java.io.*;
import java.util.ArrayList;

public class Matrix {

    protected double[][] matrix;

    public Matrix() {}

    public Matrix(String path) {
        fillFromFile(path);
    }

    public Matrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public static void print(double[] vector){
        for (double i : vector)
            System.out.print(i + " ");
        System.out.println();
    }

    public void print(String str) {
        System.out.println(str);
        print();
    }

    public void print() {
        for (double[] row : matrix)
                print(row);
    }

    public double[][] transpose(){
        double[][] transposed = new double[matrix.length][matrix.length];
        double change = 0;
        for(int i = 0; i < matrix.length; i++)
            for(int j = i; j < matrix.length; j++){
                transposed[i][j] = matrix[j][i];
                transposed[j][i] = matrix[i][j];
            }
        return transposed;
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

    private void fillFromFile(String path) {
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
}
