import java.io.*;
import java.util.ArrayList;

public class Matrix {

    // properties

    protected double[][] matrix;

    // constructors

    protected  Matrix () {

    }

    public Matrix (String path) {
        fillFromFile(path);
    }

    public Matrix (double[][] matrix) {
        this.matrix = matrix;
    }

    // public

    public double[][] getMatrix () {
        return matrix;
    }

    public void print (double[] vector){
        for (double i : vector)
            System.out.println(i + " ");
    }

    public void print () {
        for (double[] row : matrix) {
                print(row);
            System.out.println();
        }
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

    // todo
    /*public void rotation () {
        int size = matrix.length;

        double sumRowMatrix[] = new double[size];
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                if(i != j)
                    sumRowMatrix[i] += matrix[i][j] * matrix[i][j];

        int[] indexes = new int[2];

        //double[] lambda = new double[size];

        while (!(Math.abs(searchElement(sumRowMatrix, indexes)) < 0)) {
            double alpha = calculateAlpha(indexes[0], indexes[1]);
            double betta = calculateBetta(indexes[0], indexes[1]);
            UklMatrix U = new UklMatrix(size, indexes[0], indexes[1], alpha, betta);
            Matrix UT = new Matrix(MatrixMath.transposeMatrix(U.getMatrix()));

            Matrix B = new Matrix(MatrixMath.multip(UT.getMatrix(), matrix));
            Matrix matrix = new Matrix(MatrixMath.multip(B.getMatrix(), U.getMatrix()));
        }
    }*/

    // private

    private double calculateMu (int k, int l){
        return (2 * matrix[k][l]) / (matrix[k][k] - matrix[l][l]);
    }

    private double calculateAlpha (int k, int l) {
        if (matrix[k][k] == matrix[l][l])
            return Math.sqrt(0.5);
        else {
            double mu = calculateMu(k, l);
            return Math.sqrt((1 + 1 / (Math.sqrt(1 + mu * mu))) / 2);
        }
    }

    private double calculateBetta (int k, int l) {
        if (matrix[k][k] == matrix[l][l])
            return Math.sqrt(0.5);
        else {
            double mu = calculateMu(k, l);
            return Math.signum(mu) * Math.sqrt((1 - 1 / (Math.sqrt(1 + mu * mu))) / 2);
        }
    }

    private void addOneRow (String readRow, ArrayList<double[]> matrix) {
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

    private double searchElement(double[] sumRowMatrix, int[] indexes) {
        double max = 0;
        indexes[0] = 0;
        for (int i = 0; i < matrix.length; i++)
            if (max < sumRowMatrix[i]) {
                max = sumRowMatrix[i];
                indexes[0] = i;
            }

        max = 0;
        indexes[1] = 0;
        for (int i = 0; i < matrix.length; i++)
            if (max < Math.abs(matrix[indexes[0]][i])) {
                max = matrix[indexes[0]][i];
                indexes[1] = i;
            }

        if (indexes[0] > indexes[1]) {
            int change = indexes[0];
            indexes[0] = indexes[1];
            indexes[1] = change;
        }
        return max;
    }
}
