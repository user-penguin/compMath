import static java.lang.Math.abs;

/**
 * полная проблема собственных чисел
 * только для симметричных матриц
 */

public class Lab_5 extends Lab {

    public static void main(String[] arg) {
        Lab_5 lab_5 = new Lab_5();
        lab_5.fillFromFile("src\\test\\source\\file_lab_5.txt");
        lab_5.run();
        lab_5.print();
    }

    Matrix start;
    Matrix matrix;

    private double[] personalNumbers;

    private double[] sumMatrix;

    private UnitMatrix e; // мать собственных векторов

    private Matrix nevjazki;

    @Override
    public void fillFromFile(String path) {
        matrix = new Matrix(path);
        start = new Matrix(path);
    }

    @Override
    public void run() {
        sumMatrix = calcSumMatrix(matrix);
        e = new UnitMatrix(matrix.getMatrix().length);
        int[] indexes = new int[2];

        while (!(searchMaxElem(matrix.getMatrix(), indexes, sumMatrix) < MatrixMath.E)) {
            Matrix U = new Matrix(createUklMatrix(matrix.getMatrix(), indexes[0], indexes[1]));

            // A = UT * A * U
            matrix = new Matrix(MatrixMath.multip(U.transpose(), matrix.getMatrix()));
            matrix = new Matrix(MatrixMath.multip(matrix.getMatrix(), U.getMatrix()));

            // e = e * U
            e = new UnitMatrix(MatrixMath.multip(e.getMatrix(), U.getMatrix()));

            sumMatrix = calcSumMatrix(matrix);
        }

        personalNumbers = new double[matrix.getMatrix().length];
        for (int i = 0; i < personalNumbers.length; i++)
            personalNumbers[i] = matrix.getMatrix()[i][i];

        normalization(e.getMatrix());

        nevjazki = new Matrix(new double[matrix.getMatrix().length][matrix.getMatrix().length]);
        nevjazka(start.getMatrix(), personalNumbers, e.getMatrix(), nevjazki.getMatrix());
    }

    @Override
    public void print() {
        matrix.print("Исходная матрица: ");

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

    private static double[] calcSumMatrix(Matrix matrix) {
        int n = matrix.getMatrix().length;
        double sumMatrix[] = new double[n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (i != j)
                    sumMatrix[i] += matrix.getMatrix()[i][j] * matrix.getMatrix()[i][j];

        return sumMatrix;
    }

    private static double calcMu(double[][] matrix, int k, int l){
        return (2 * matrix[k][l]) / (matrix[k][k] - matrix[l][l]);
    }

    private static double calcAlpha(double[][] matrix, int k, int l) {
        if (matrix[k][k] == matrix[l][l])
            return Math.sqrt(0.5);
        else {
            double mu = calcMu(matrix, k, l);
            return Math.sqrt((1 + 1 / (Math.sqrt(1 + mu * mu))) / 2);
        }
    }

    private static double calcBetta(double[][] matrix, int k, int l) {
        if (matrix[k][k] == matrix[l][l])
            return Math.sqrt(0.5);
        else {
            double mu = calcMu(matrix, k, l);
            return sign(mu) * Math.sqrt((1 - 1 / (Math.sqrt(1 + mu * mu))) / 2);
        }
    }

    private static double[][] createUklMatrix(double[][] matrix, int k, int l) {
        int size = matrix.length;
        double[][] secondMatrix = new double[size][size];

        for(int i = 0; i < size; i++)
            secondMatrix[i][i] = 1;

        secondMatrix[k][k] = secondMatrix[l][l] = calcAlpha(matrix, k, l);
        secondMatrix[k][l] = -1 * calcBetta(matrix, k, l);
        secondMatrix[l][k] = calcBetta(matrix, k, l);

        return secondMatrix;
    }

    private static double searchMaxElem(double[][] matrix, int[] indexes, double[] sumMatrix) {
        double max = 0;
        for(int i = 0; i < matrix.length; i++)
            if(sumMatrix[i] > max) {
                max = sumMatrix[i];
                indexes[0] = i;
            }

        max = 0;
        for(int i = 0; i < matrix.length; i++)
            if((Math.abs(matrix[indexes[0]][i]) > Math.abs(max)) && (indexes[0] != i)) {
                max = matrix[indexes[0]][i];
                indexes[1] = i;
            }

        if(indexes[0] > indexes[1]){
            int a = indexes[0];
            indexes[0] = indexes[1];
            indexes[1] = a;
        }

        return Math.abs(max);
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
