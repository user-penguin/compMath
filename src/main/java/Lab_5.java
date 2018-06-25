import static java.lang.Math.abs;

/**
 * полная проблема собственных чисел
 * только для симметричных матриц
 * https://lektsii.org/13-70062.html
 */

public class Lab_5 {

    public static void main(String[] arg) {
        Lab_5 lab_5 = new Lab_5("src\\test\\source\\file_lab_5.txt");
    }

    // constructors

    public Lab_5(String path) {
        Matrix start = new Matrix(path);
        Matrix A = new Matrix(path);
        double[] sumA = calculateSumA(A.getMatrix());
        UnitMatrix e = new UnitMatrix(A.getMatrix().length);
        int[] indexes = new int[2];

        while (!(searchMaxElem(A.getMatrix(), indexes, sumA) < MatrixMath.E)) {
            Matrix U = new Matrix(uklMatrix(A.getMatrix(), indexes[0], indexes[1]));

            // A = UT * A * U
            A = new Matrix(MatrixMath.multip(U.transpose(), A.getMatrix()));
            A = new Matrix(MatrixMath.multip(A.getMatrix(), U.getMatrix()));

            e = new UnitMatrix(MatrixMath.multip(e.getMatrix(), U.getMatrix()));

            sumA = calculateSumA(A.getMatrix());
        }

        double[] personalNumbers = new double[A.getMatrix().length];
        for (int i = 0; i < personalNumbers.length; i++)
            personalNumbers[i] = A.getMatrix()[i][i];

        normalization(e.getMatrix());

        Matrix nevjazki = new Matrix(new double[A.getMatrix().length][A.getMatrix().length]);
        nevjazka(start.getMatrix(), personalNumbers, e.getMatrix(), nevjazki.getMatrix());

        print(personalNumbers, e, nevjazki, start);
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
