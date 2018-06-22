/**
 * частичная проблема собственных чисел
 */

public class Lab_4 {

    private double personalNumbers;
    private double[] personalVector;
    private double[] firstApproximation;

    public Lab_4(String path) {
        Matrix matrix = new Matrix(path);

        firstApproximation = MatrixMath.getRandomVector(matrix.getMatrix().length);
        personalVector = MatrixMath.searchPersonalVector(matrix.getMatrix(), firstApproximation);
        personalNumbers = MatrixMath.searchPersonalNumber(matrix.getMatrix(), personalVector);



        matrix.print();
    }
}
