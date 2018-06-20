import java.util.Random;

public class MatrixMath {

    static double E = 0.001;
    static double MAX_NUM_OF_ITER = 1_000;

    // перемножение матриц
    static double[] multipMatrixVector(double[][] A, double[] firstVector){
        int width = A.length;
        double[] finalMatrix = new double[width];
        for(int i = 0; i < width; i++)
            finalMatrix[i] = multipVector(A[i], firstVector);
        return finalMatrix;
    }

    // строка на вектор
    private static double multipVector(double[] row, double[] vector){
        double result = 0;
        for(int i = 0; i < vector.length; i++)
            result += row[i] * vector[i];
        return result;
    }

    // сравнение векторов
    static boolean compareVectors(double[] vectorPrev, double[] vectorNext){
        for (int i = 0; i < vectorNext.length; i++)
            if (!equals(vectorPrev[i], vectorNext[i]))
                return false;
        return true;
    }

    // сравнение чисел
    private static boolean equals(double num1, double num2) {
        if (num1 - num2 < E)
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

    static double[] searchPersonalVector(double[][] matrix, Double personalNumber) {
        double[] vectorFirst = normalization(getRandomVector(matrix.length));
        double[] vectorSecond = new double[matrix.length];

        for (int i = 0; i < MAX_NUM_OF_ITER; i++) {
            vectorSecond = multipMatrixVector(matrix, vectorFirst);
            vectorSecond = normalization(vectorSecond);

            //todo сравнение не векторов а собственных чисел
            personalNumber = scalarMultipVector(vectorFirst, vectorSecond) / scalarMultipVector(vectorFirst, vectorFirst);
            System.out.println(personalNumber);

            if (compareVectors(vectorFirst, vectorSecond) ||
                    compareVectors(multipVectorNumber(vectorFirst, -1), vectorSecond))
                break;

            double[] changeble = vectorFirst;
            vectorFirst = vectorSecond;
            vectorSecond = changeble;
        }
        return vectorSecond;
    }

    private static double[] getRandomVector(int length) {
        Random random = new Random();
        double[] vector = new double[length];
        for (int i = 0; i < length; i++)
            vector[i] = 1;
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
