import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MatrixMath {

    static double E = 0.001;
    static double MAX_NUM_OF_ITER = 1_000;

    static double[] multipMatrixVector(double[][] A, double[] firstVector){
        int width = A.length;
        double[] finalMatrix = new double[width];
        for(int i = 0; i < width; i++)
            finalMatrix[i] = multipVector(A[i], firstVector);
        return finalMatrix;
    }

    private static double multipVector(double[] row, double[] vector){
        double result = 0;
        for(int i = 0; i < vector.length; i++)
            result += row[i] * vector[i];
        return result;
    }

    static boolean compareVectors(double[] vectorPrev, double[] vectorNext){
        for (int i = 0; i < vectorNext.length; i++)
            if (!equals(vectorPrev[i], vectorNext[i]))
                return false;
        return true;
    }

    public static boolean equals(double num1, double num2) {
        if (Math.abs(num1 - num2) < E)
            return true;
        else
            return false;
    }

    static double[] normalization(double[] vector) {
        double max = Math.abs(vector[0]);
        for (double i : vector)
            if (max < Math.abs(i))
                max = Math.abs(i);

        double[] normVector = new double[vector.length];
        for (int i = 0; i < vector.length; i++)
            normVector[i] = vector[i] / max;
        return normVector;
    }

    static double[] searchPersonalVector (double[][] matrix) {
        double[] vectorFirst = getRandomVector(matrix.length);
        double[] vectorSecond = new double[matrix.length];

        double personalNumberSec = 1;

        for (int i = 0; i < MAX_NUM_OF_ITER; i++) {
            vectorSecond = multipMatrixVector(matrix, vectorFirst);
            vectorSecond = normalization(vectorSecond);

            if (compareVectors(vectorFirst, vectorSecond))
                break;

            vectorFirst = vectorSecond;
        }

        return vectorSecond;
    }

    static double searchPersonalNumber(double[][] matrix, double[] vector){
        double[] vectorSecond = multipMatrixVector(matrix, vector);
        double personalNumber = scalarMultipVector(vector, vectorSecond) / scalarMultipVector(vector, vector);
        return personalNumber;
    }

    private static double[] getRandomVector(int length) {
        Random random = new Random();
        double[] vector = new double[length];
        for (int i = 0; i < length; i++)
            vector[i] = ThreadLocalRandom.current().nextDouble(1, 20);
        return vector;
    }

    static double[][] transposeMatrix(double[][] matrix){
        double[][] transposed = new double[matrix.length][matrix.length];
        double change = 0;
        for(int i = 0; i < matrix.length; i++)
            for(int j = i; j < matrix.length; j++){
                transposed[i][j] = matrix[j][i];
                transposed[j][i] = matrix[i][j];
            }

        return transposed;
    }

    //тестовый коммент. сохранится или нет?

    static double[] multipVectorNumber(double[] vector, double nunber) {
        double[] vectorNew = new double[vector.length];
        for (int i = 0; i < vector.length; i++)
            vectorNew[i] = vector[i] * nunber;
        return vectorNew;
    }

    static double scalarMultipVector(double[] vec1, double[] vec2) {
        double scalar = 0;
        for (int i = 0; i < vec1.length; i++)
            scalar += vec1[i] * vec2[i];
        return scalar;
    }
}
