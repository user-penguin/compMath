import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MatrixMath {

    // properties

    static double E = 0.001;
    static double MAX_NUM_OF_ITER = 1_000;

    // public

    public static double[] multip(double[][] A, double[] vector){
        int width = A.length;
        double[] finalMatrix = new double[width];
        for(int i = 0; i < width; i++)
            finalMatrix[i] = multipVector(A[i], vector);
        return finalMatrix;
    }

    public static double[][] multip(double[][] A, double[][] B) {
        int width = B[0].length;
        int height = A.length;
        double[][] finalMatrix = new double[height][];

        for (int i = 0; i < height; i++) {
            double[] vector = new double[B.length];
            for (int j = 0; j < B.length; j++)
                vector[j] = B[j][i];
            finalMatrix[i] = multip(A, vector);
        }
        return new Matrix(finalMatrix).transpose();
    }

    public static boolean equals(double num1, double num2) {
        if (Math.abs(num1 - num2) < E)
            return true;
        else
            return false;
    }

    // private

    private static double multipVector(double[] row, double[] vector){
        double result = 0;
        for(int i = 0; i < vector.length; i++)
            result += row[i] * vector[i];
        return result;
    }

    private static double[] normalization(double[] vector) {
        double max = Math.abs(vector[0]);
        for (double i : vector)
            if (max < Math.abs(i))
                max = Math.abs(i);

        double[] normVector = new double[vector.length];
        for (int i = 0; i < vector.length; i++)
            normVector[i] = vector[i] / max;
        return normVector;
    }





    static boolean compareVectors(double[] vectorPrev, double[] vectorNext){
        for (int i = 0; i < vectorNext.length; i++)
            if (!equals(vectorPrev[i], vectorNext[i]))
                return false;
        return true;
    }

    static double[] searchPersonalVector (double[][] matrix, double[] firstApproximation) {
        double[] vectorFirst = firstApproximation;
        double[] vectorSecond = new double[matrix.length];

        double personalNumberSec = 1;

        for (int i = 0; i < MAX_NUM_OF_ITER; i++) {
            vectorSecond = multip(matrix, vectorFirst);
            vectorSecond = normalization(vectorSecond);

            if (compareVectors(vectorFirst, vectorSecond))
                break;

            vectorFirst = vectorSecond;
        }

        return vectorSecond;
    }

    static double searchPersonalNumber(double[][] matrix, double[] vector){
        double[] vectorSecond = multip(matrix, vector);
        double personalNumber = scalarMultipVector(vector, vectorSecond) / scalarMultipVector(vector, vector);
        return personalNumber;
    }

    static double[] searchSecPersVector (double[][] matrix, double[] personalVector, double[] firstApproximation) {

        double[][] transpMatrix = new Matrix(matrix).transpose();
        double[] persVectorOfTranspose = searchPersonalVector(transpMatrix, firstApproximation);
        double firstRatio = scalarMultipVector(firstApproximation, persVectorOfTranspose) /
                scalarMultipVector(personalVector, persVectorOfTranspose);
        double[] firstApproxTransp = substract(firstApproximation, multipVectorNumber(personalVector, firstRatio));
        double[] secApproxTransp = new double[personalVector.length];

        for (int i = 0; i < MAX_NUM_OF_ITER; i++) {
            secApproxTransp = multip(transpMatrix, firstApproxTransp);
            secApproxTransp = normalization(secApproxTransp);
            secApproxTransp = getProportion(secApproxTransp, persVectorOfTranspose, personalVector);

            if (compareVectors(firstApproxTransp, secApproxTransp))
                break;

            firstApproxTransp = secApproxTransp;
        }

        return secApproxTransp;
    }

    static double[] getProportion (double[] approx, double[] personVectorTransp, double[] personVectorUsual) {
        double component = scalarMultipVector(approx, personVectorTransp) /
                scalarMultipVector(personVectorUsual, personVectorTransp);
        return substract(approx, multipVectorNumber(personVectorUsual, component));
    }

    static double[] substract(double[] firstVector, double[] secondVector){
        double[] result = new double[firstVector.length];
        for(int i = 0; i < firstVector.length; i++)
            result[i] = firstVector[i] - secondVector[i];

        return result;
    }

    public static double[] getRandomVector(int length) {
        Random random = new Random();
        double[] vector = new double[length];
        for (int i = 0; i < length; i++)
            vector[i] = ThreadLocalRandom.current().nextDouble(1, 20);
        return vector;
    }



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
