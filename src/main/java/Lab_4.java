import java.util.Scanner;

/**
 * частичная проблема собственных чисел
 */

public class Lab_4 {
    public static void main(String[] arg) {
        Lab_4 lab_4 = new Lab_4("src\\test\\source\\in41.txt");
    }

    // properties

    private double personalNumbers;

    private double[] personalVector;

    private double[] firstApproximation;

    // constructors

    public Lab_4(String path) {
        Matrix matrix = new Matrix(path);

        firstApproximation = MatrixMath.getRandomVector(matrix.getMatrix().length);
        personalVector = searchPersonalVector(matrix.getMatrix(), firstApproximation);
        personalNumbers = searchPersonalNumber(matrix.getMatrix(), personalVector);

        System.out.println("Собственный вектор, соответствующий " +
                "наибольшему по модулю собственному числу:");
        new Matrix().print(personalVector);
        System.out.println("Наибольшее по модулю собственное число = " + personalNumbers);

        double[] secondPersonalVector = searchSecPersVector(matrix.getMatrix(),
                personalVector, firstApproximation);
        double secondPersonalNumber = searchPersonalNumber(matrix.getMatrix(), secondPersonalVector);


        System.out.println("Собственный вектор, соответствующий " +
                "второму по абсолютной величине собственному числу:");
        new Matrix().print(secondPersonalVector);
        System.out.println("Второе по абсолютной величине собственное число = " + secondPersonalNumber);


        System.out.println("Введите число, к которому будем искать ближайшее собственное");
        Scanner in = new Scanner(System.in);


        double nearNumber = in.nextDouble();
        System.out.println("Ближайшее к " + nearNumber + "= " +
                searchCloserNumber(nearNumber, matrix.getMatrix()));
    }


    // public

    public static double[] calculateProportion(double[] approx, double[] personVectorTransp, double[] personVectorUsual) {
        double component = MatrixMath.scalarMultip(approx, personVectorTransp) /
                MatrixMath.scalarMultip(personVectorUsual, personVectorTransp);
        return MatrixMath.substract(approx, MatrixMath.multip(personVectorUsual, component));
    }

    public static double[] searchPersonalVector (double[][] matrix, double[] firstApproximation) {
        double[] vectorFirst = firstApproximation;
        double[] vectorSecond = new double[matrix.length];

        double personalNumberSec = 1;

        for (int i = 0; i < MatrixMath.MAX_NUM_OF_ITER; i++) {
            vectorSecond = MatrixMath.multip(matrix, vectorFirst);
            vectorSecond = MatrixMath.normalization(vectorSecond);

            if (MatrixMath.equals(vectorFirst, vectorSecond))
                break;

            vectorFirst = vectorSecond;
        }

        return vectorSecond;
    }

    public static double searchCloserNumber (double sourceNum, double[][] matrix) {
        double[][] matrixB = findMatrixB(matrix, sourceNum);
        double[] firstVector = searchPersonalVector(matrixB, MatrixMath.getRandomVector(matrix.length));
        double[] secondVector = MatrixMath.multip(matrixB, firstVector);

        return sourceNum + (MatrixMath.scalarMultip(firstVector, firstVector) / MatrixMath.scalarMultip(secondVector, firstVector));
    }

    public static double[] searchSecPersVector (double[][] matrix, double[] personalVector, double[] firstApproximation) {

        double[][] transpMatrix = new Matrix(matrix).transpose();
        double[] persVectorOfTranspose = searchPersonalVector(transpMatrix, firstApproximation);
        double firstRatio = MatrixMath.scalarMultip(firstApproximation, persVectorOfTranspose) /
                MatrixMath.scalarMultip(personalVector, persVectorOfTranspose);
        double[] firstApproxTransp = MatrixMath.substract(firstApproximation, MatrixMath.multip(personalVector, firstRatio));
        double[] secApproxTransp = new double[personalVector.length];

        for (int i = 0; i < MatrixMath.MAX_NUM_OF_ITER; i++) {
            secApproxTransp = MatrixMath.multip(transpMatrix, firstApproxTransp);
            secApproxTransp = MatrixMath.normalization(secApproxTransp);
            secApproxTransp = calculateProportion(secApproxTransp, persVectorOfTranspose, personalVector);

            if (MatrixMath.equals(firstApproxTransp, secApproxTransp))
                break;

            firstApproxTransp = secApproxTransp;
        }

        return secApproxTransp;
    }

    public static double searchPersonalNumber(double[][] matrix, double[] vector){
        double[] vectorSecond = MatrixMath.multip(matrix, vector);
        double personalNumber = MatrixMath.scalarMultip(vector, vectorSecond) / MatrixMath.scalarMultip(vector, vector);
        return personalNumber;
    }

    public static double[][] findMatrixB (double[][] matrix, double personalNumber) {
        double[][] matrixA = matrix;
        UnitMatrix Umatrix = new UnitMatrix(matrix.length);
        double[][] unitMatrix = Umatrix.getMatrix();

        return MatrixMath.reverseMatrix(MatrixMath.substract(matrixA, MatrixMath.multip(unitMatrix, personalNumber)));
    }
}
