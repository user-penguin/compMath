import java.util.Scanner;

/**
 * частичная проблема собственных чисел
 */

public class Lab_4 extends Lab {

    public static void main(String[] arg) {
        Lab_4 lab_4 = new Lab_4();
        lab_4.fillFromFile("src\\test\\source\\file_lab_4.txt");
        lab_4.run();
        lab_4.print();
    }

    Matrix matrix;

    private double[] firstApproximation;

    private double firstPersonalNumbers;
    private double[] firstPersonalVector;

    double secondPersonalNumber;
    double[] secondPersonalVector;

    double nearNumber;
    double nearPersonalNumber;

    @Override
    public void fillFromFile(String path) {
        matrix = new Matrix(path);
    }

    @Override
    public void run() {
        firstApproximation = MatrixMath.getRandomVector(matrix.getMatrix().length);

        firstPersonalVector = searchPersonalVector(matrix.getMatrix(), firstApproximation);
        firstPersonalNumbers = searchFirstPersonalNumber(matrix.getMatrix(), firstPersonalVector);

        secondPersonalVector = searchSecondPersonalVector(matrix.getMatrix(), firstPersonalVector, firstApproximation);
        secondPersonalNumber = searchFirstPersonalNumber(matrix.getMatrix(), secondPersonalVector);

        System.out.println("Введите число, к которому будем искать ближайшее собственное");
        Scanner in = new Scanner(System.in);

        nearNumber = in.nextDouble();
        nearPersonalNumber = searchCloserNumber(nearNumber, matrix.getMatrix());
    }

    @Override
    public void print() {
        matrix.print("Исходная матрица");

        System.out.println("Собственный вектор, соответствующий наибольшему по модулю собственному числу:");
        Matrix.print(firstPersonalVector);
        System.out.println("Наибольшее по модулю собственное число = " + firstPersonalNumbers);

        System.out.println("Собственный вектор, соответствующий второму по абсолютной величине собственному числу:");
        Matrix.print(secondPersonalVector);
        System.out.println("Второе по абсолютной величине собственное число = " + secondPersonalNumber);

        System.out.println("Ближайшее к " + nearNumber + " = " + nearPersonalNumber);
    }

    public static double[] searchPersonalVector(double[][] matrix, double[] vectorFirstApproximation) {
        double[] vectorFirst = vectorFirstApproximation;
        double[] vectorSecond = new double[matrix.length];

        for (int i = 0; i < MatrixMath.MAX_NUM_OF_ITER; i++) {
            vectorSecond = MatrixMath.multip(matrix, vectorFirst);
            vectorSecond = MatrixMath.normalization(vectorSecond);

            if (MatrixMath.equals(vectorFirst, vectorSecond))
                break;

            vectorFirst = vectorSecond;
        }

        return vectorSecond;
    }

    public static double searchFirstPersonalNumber(double[][] matrix, double[] personalVector){
        double[] secondPersonalVector = MatrixMath.multip(matrix, personalVector);
        double personalNumber = MatrixMath.scalarMultip(personalVector, secondPersonalVector) /
                MatrixMath.scalarMultip(personalVector, personalVector);
        return personalNumber;
    }

    public static double[] searchSecondPersonalVector(double[][] matrix, double[] firstPersonalVector, double[] vectorFirstApproximation) {
        double[][] transposeMatrix = new Matrix(matrix).transpose();
        double[] personalVectorOfTranspose = searchPersonalVector(transposeMatrix, vectorFirstApproximation);

        // y(0) = x(0) - (x(0), g1) / (e1, g1)
        double firstRatio = MatrixMath.scalarMultip(vectorFirstApproximation, personalVectorOfTranspose) /
                MatrixMath.scalarMultip(firstPersonalVector, personalVectorOfTranspose);
        double[] vectorFirstApproximationTransp = MatrixMath.substract(vectorFirstApproximation,
                MatrixMath.multip(firstPersonalVector, firstRatio));

        double[] secondApproximationTranspose = new double[firstPersonalVector.length];
        for (int i = 0; i < MatrixMath.MAX_NUM_OF_ITER; i++) {
            secondApproximationTranspose = MatrixMath.multip(transposeMatrix, vectorFirstApproximationTransp);
            secondApproximationTranspose = MatrixMath.normalization(secondApproximationTranspose);
            secondApproximationTranspose = calcProportion(secondApproximationTranspose, personalVectorOfTranspose, firstPersonalVector);

            if (MatrixMath.equals(vectorFirstApproximationTransp, secondApproximationTranspose))
                break;

            vectorFirstApproximationTransp = secondApproximationTranspose;
        }

        return secondApproximationTranspose;
    }

    public static double[] calcProportion(double[] approx, double[] personVectorTransp, double[] personVectorUsual) {
        double component = MatrixMath.scalarMultip(approx, personVectorTransp) /
                MatrixMath.scalarMultip(personVectorUsual, personVectorTransp);
        return MatrixMath.substract(approx, MatrixMath.multip(personVectorUsual, component));
    }

    public static double searchCloserNumber (double sourceNum, double[][] matrix) {
        double[][] matrixB = findMatrixB(matrix, sourceNum);
        double[] firstVector = searchPersonalVector(matrixB, MatrixMath.getRandomVector(matrix.length));
        double[] secondVector = MatrixMath.multip(matrixB, firstVector);

        return sourceNum + (MatrixMath.scalarMultip(firstVector, firstVector) / MatrixMath.scalarMultip(secondVector, firstVector));
    }

    public static double[][] findMatrixB (double[][] matrix, double personalNumber) {
        double[][] matrixA = matrix;
        double[][] unitMatrix = new UnitMatrix(matrix.length).getMatrix();

        return MatrixMath.reverseMatrix(MatrixMath.substract(matrixA, MatrixMath.multip(unitMatrix, personalNumber)));
    }
}
