import static java.lang.Math.abs;

/**
 * полная проблема собственных чисел
 * только для симметричных матриц
 * https://lektsii.org/13-70062.html
 */

public class Lab_5 {

    public static void main(String[] arg) {
        Matrix A = new Matrix("input.txt");
        A.print("Исходная матрица A: ");
        double[] sumA = calculateSumA(A.getMatrix());

        int[] indexes = new int[2];
        while (!(searchMaxElem(A.getMatrix(), indexes, sumA) < MatrixMath.E)) {
            Matrix U = new Matrix(uklMatrix(A.getMatrix(), indexes[0], indexes[1]));

            // A = UT * A * U
            A = new Matrix(MatrixMath.multip(U.transpose(), A.getMatrix()));
            A = new Matrix(MatrixMath.multip(A.getMatrix(), U.getMatrix()));

            sumA = calculateSumA(A.getMatrix());
        }

        double[] personalNumbers = new double[A.getMatrix().length];
        for (int i = 0; i < personalNumbers.length; i++)
            personalNumbers[i] = A.getMatrix()[i][i];

        System.out.println("\nСобственные числа: ");
        for (double i : personalNumbers)
            System.out.println(i);
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
                //System.out.println(indexes[0] + " " + indexes[1] + " " + max);
            }

        max = 0;
        for(int i = 0; i < A.length; i++)
            if((Math.abs(A[indexes[0]][i]) > Math.abs(max)) && (indexes[0] != i)) {
                max = A[indexes[0]][i];
                indexes[1] = i;
                //System.out.println(indexes[0] + " " + indexes[1] + " " + max);
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
}
