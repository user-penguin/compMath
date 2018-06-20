import java.io.*;
import java.util.ArrayList;

public class Matrix {

    private double[][] matrix;

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
        for (int i = 0; i < vector.length; i++)
            System.out.println(vector[i]);
    }

    public void print(double[][] matrix) {
        for (double[] row : matrix) {
            for (double i : row) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    public double[][] toArray(ArrayList<double[]> matrixArr) {
        double[][] matrix = new double[matrixArr.size()][matrixArr.get(0).length];
        for(int i = 0; i < matrixArr.size(); i++)
            for(int j = 0; j < matrixArr.get(0).length; j++)
                matrix[i][j] = matrixArr.get(i)[j];
        return matrix;
    }

    public double[][] getMatrix() {
        return matrix;
    }
}
