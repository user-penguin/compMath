public class MatrixMath {

    static double E = 0.001;

    // перемножение матриц
    public double[] multipMatrixVector(double[][] A, double[] firstVector){
        int width = A.length;
        double[] finalMatrix = new double[width];
        for(int i = 0; i < width; i++)
            finalMatrix[i] = multipVector(A[i], firstVector);
        return finalMatrix;
    }

    // строка на вектор
    private double multipVector(double[] row, double[] vector){
        double result = 0;
        for(int i = 0; i < vector.length; i++)
            result += row[i] * vector[i];
        return result;
    }

    // сравнение векторов
    public boolean compareVectors(double[] vectorPrev, double[] vectorNext){
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

    public double[] normalization(double[] vector) {
        double max = Math.abs(vector[0]);
        for (double i : vector)
            if (max < Math.abs(i))
                max = Math.abs(i);

        double[] normVector = new double[vector.length];
        for (int i = 0; i < vector.length; i++)
            normVector[i] = vector[i] / max;
        return normVector;
    }
}
