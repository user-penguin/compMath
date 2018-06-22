import java.util.Scanner;

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

        System.out.println("Собственный вектор, соответствующий " +
                "наибольшему по модулю собственному числу:");
        new Matrix().print(personalVector);
        System.out.println("Наибольшее по модулю собственное число = " + personalNumbers);

        double[] secondPersonalVector = MatrixMath.searchSecPersVector(matrix.getMatrix(),
                personalVector, firstApproximation);
        double secondPersonalNumber = MatrixMath.searchPersonalNumber(matrix.getMatrix(), secondPersonalVector);


        System.out.println("Собственный вектор, соответствующий " +
                "второму по абсолютной величине собственному числу:");
        new Matrix().print(secondPersonalVector);
        System.out.println("Второе по абсолютной величине собственное число = " + secondPersonalNumber);


        System.out.println("Введите число, к которому будем искать ближайшее собственное");
        Scanner in = new Scanner(System.in);


        double nearNumber = in.nextDouble();
        System.out.println("Ближайшее к " + nearNumber + "= " +
                MatrixMath.searchCloserNumber(nearNumber, matrix.getMatrix()));

    }
}
