import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;


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
            finalMatrix[i] = multip(A[i], vector);
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

    public static double[] multip(double[] vector, double number) {
        double[] vectorNew = new double[vector.length];
        for (int i = 0; i < vector.length; i++)
            vectorNew[i] = vector[i] * number;
        return vectorNew;
    }

    public static double[][] multip(double[][] A, double b) {
        int width = A[0].length;
        int height = A.length;
        double[][] finalMatrix = new double[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++)
                finalMatrix[i][j] = A[i][j] * b;
        }
        return finalMatrix;
    }

    public static double scalarMultip(double[] vector1, double[] vector2) {
        double scalar = 0;
        for (int i = 0; i < vector1.length; i++)
            scalar += vector1[i] * vector2[i];
        return scalar;
    }

    public static double[] calculateProportion(double[] approx, double[] personVectorTransp, double[] personVectorUsual) {
        double component = scalarMultip(approx, personVectorTransp) /
                scalarMultip(personVectorUsual, personVectorTransp);
        return substract(approx, multip(personVectorUsual, component));
    }

    public static double[] substract(double[] vector1, double[] vector2){
        double[] result = new double[vector1.length];
        for(int i = 0; i < vector1.length; i++)
            result[i] = vector1[i] - vector2[i];
        return result;
    }

    public static double[][] substract(double[][] matrix1, double[][] matrix2) {
        double[][] result = new double[matrix1.length][matrix1.length];
        for (int i = 0; i < matrix1.length; i++)
            for (int j = 0; j < matrix1.length; j++)
                result[i][j] = matrix1[i][j] - matrix2[i][j];
        return result;
    }

    public static boolean equals(double num1, double num2) {
        if (Math.abs(num1 - num2) < E)
            return true;
        else
            return false;
    }

    public static boolean equals(double[] vector1, double[] vector2){
        for (int i = 0; i < vector2.length; i++)
            if (!equals(vector1[i], vector2[i]))
                return false;
        return true;
    }

    public static double[] searchPersonalVector (double[][] matrix, double[] firstApproximation) {
        double[] vectorFirst = firstApproximation;
        double[] vectorSecond = new double[matrix.length];

        double personalNumberSec = 1;

        for (int i = 0; i < MAX_NUM_OF_ITER; i++) {
            vectorSecond = multip(matrix, vectorFirst);
            vectorSecond = normalization(vectorSecond);

            if (equals(vectorFirst, vectorSecond))
                break;

            vectorFirst = vectorSecond;
        }

        return vectorSecond;
    }

    public static double searchCloserNumber (double sourceNum, double[][] matrix) {
        double[][] matrixB = findMatrixB(matrix, sourceNum);
        double[] firstVector = searchPersonalVector(matrixB, getRandomVector(matrix.length));
        double[] secondVector = multip(matrixB, firstVector);

        return sourceNum + (scalarMultip(firstVector, firstVector) / scalarMultip(secondVector, firstVector));
    }

    public static double[] searchSecPersVector (double[][] matrix, double[] personalVector, double[] firstApproximation) {

        double[][] transpMatrix = new Matrix(matrix).transpose();
        double[] persVectorOfTranspose = searchPersonalVector(transpMatrix, firstApproximation);
        double firstRatio = scalarMultip(firstApproximation, persVectorOfTranspose) /
                scalarMultip(personalVector, persVectorOfTranspose);
        double[] firstApproxTransp = substract(firstApproximation, multip(personalVector, firstRatio));
        double[] secApproxTransp = new double[personalVector.length];

        for (int i = 0; i < MAX_NUM_OF_ITER; i++) {
            secApproxTransp = multip(transpMatrix, firstApproxTransp);
            secApproxTransp = normalization(secApproxTransp);
            secApproxTransp = calculateProportion(secApproxTransp, persVectorOfTranspose, personalVector);

            if (equals(firstApproxTransp, secApproxTransp))
                break;

            firstApproxTransp = secApproxTransp;
        }

        return secApproxTransp;
    }

    public static double searchPersonalNumber(double[][] matrix, double[] vector){
        double[] vectorSecond = multip(matrix, vector);
        double personalNumber = scalarMultip(vector, vectorSecond) / scalarMultip(vector, vector);
        return personalNumber;
    }

    public static double[] getRandomVector(int length) {
        Random random = new Random();
        double[] vector = new double[length];
        for (int i = 0; i < length; i++)
            vector[i] = ThreadLocalRandom.current().nextDouble(1, 20);
        return vector;
    }

    public static double[][] reverseMatrix (double[][] matrix) {
        UnitMatrix Umatrix = new UnitMatrix(matrix.length);
        double[][] unitMatrix = Umatrix.getMatrix();
        RealMatrix sourceMatrix = new Array2DRowRealMatrix(matrix);
        DecompositionSolver solver = new LUDecomposition(sourceMatrix).getSolver();
        RealMatrix I = new Array2DRowRealMatrix(unitMatrix);
        RealMatrix result = solver.solve(I);

        return result.getData();
    }

    public static double[][] findMatrixB (double[][] matrix, double personalNumber) {
        double[][] matrixA = matrix;
        UnitMatrix Umatrix = new UnitMatrix(matrix.length);
        double[][] unitMatrix = Umatrix.getMatrix();

        return reverseMatrix(substract(matrixA, multip(unitMatrix, personalNumber)));
    }

    public static double[] normalization(double[] vector) {
        double max = Math.abs(vector[0]);
        for (double i : vector)
            if (max < Math.abs(i))
                max = Math.abs(i);

        double[] normVector = new double[vector.length];
        for (int i = 0; i < vector.length; i++)
            normVector[i] = vector[i] / max;
        return normVector;
    }

    // private

    private static double multip(double[] row, double[] vector){
        double result = 0;
        for(int i = 0; i < vector.length; i++)
            result += row[i] * vector[i];
        return result;
    }
}
