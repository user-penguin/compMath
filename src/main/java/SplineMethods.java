import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SplineMethods {
    private double[] iNodes;
    private double[] iValues;

    private String typeOfFunct;
    private double interpolateStep;
    private double localStep;
    private double leftBorder;
    private double rightBorder;

    private double[] B;
    private double[] C;
    private double[] D;
    private double[] freeB;

    SplineMethods() {

    }

    SplineMethods(String func) {
        typeOfFunct = func;
    }

    public void setTypeOfFunc(String function) {
        typeOfFunct = function;
    }

    public void setInterpolateStep(double step) {
        interpolateStep = step;
    }

    public void setLocalStep(double step) {
        localStep = step;
    }

    public void setLeftBorder(double border) {
        leftBorder = border;
    }

    public void setRightBorder(double border) {
        rightBorder = border;
    }

    public double getInterpolateStep() {
        return interpolateStep;
    }

    public double getLocalStep() {
        return localStep;
    }

    public String getTypeOfFunct() {
        return typeOfFunct;
    }

    public double[] getiNodes() {
        return iNodes;
    }

    public double[] getiValues() {
        return iValues;
    }

    public void fillSplineData(String path) {
        try {
            FileInputStream fstream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            setInterpolateStep(Double.parseDouble(br.readLine()));
            setLocalStep(Double.parseDouble(br.readLine()));
            setLeftBorder(Double.parseDouble(br.readLine()));
            setRightBorder(Double.parseDouble(br.readLine()));

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void calcSplineArguments()   {
        int size = (int) ((rightBorder - leftBorder) / interpolateStep) + 1;
        iNodes = new double[size];
        iValues = new double[size];

        int i = 0;
        double currentNode = leftBorder;
        while (currentNode - MatrixMath.E <= rightBorder) {
            iNodes[i] = currentNode;
            currentNode += interpolateStep;
            i++;
        }
    }

    public void calcSplineValues() {
        iValues = new double[iNodes.length];
        for (int i = 0; i < iNodes.length; i++)
            iValues[i] = (new InterpolMethods()).calcValueAvailFunction(typeOfFunct, iNodes[i]);
    }

    public double[] transformListToArray(ArrayList<Double> source) {
        double[] arrayNodes = new double[source.size()];
        for (int i = 0; i < source.size(); i++)
            arrayNodes[i] = source.get(i);

        return arrayNodes;
    }

    // x[i] - x[i-1]
    public double dX(int i) {
        return iNodes[i] - iNodes[i - 1];
    }

    // y[i] - y[i-1]
    public double dY(int i) {
        return iValues[i] - iValues[i - 1];
    }

    public double[][] getTridiagonalMatrix() {
        double[][] C = new double[iNodes.length - 2][iNodes.length - 2];
        freeB = new double[iNodes.length - 2];
        for(int i = 1; i < C.length - 1; i++) {
            C[i][i - 1] = dX(i) / 6;
            C[i][i] = (-1) * (dX(i) / 3 + dX(i + 1) / 3);
            C[i][i + 1] = dX(i + 1) / 6;
            freeB[i] = (dY(i + 1) / dX(i + 1)) - (dY(i) / dX(i));
        }

        C[0][0] = (-1) * (dX(1) / 3 + dX(2) / 3);
        C[0][1] = (dX(2)) / 6;
        freeB[0] = dY(2) / dX(2) - dY(1) / dX(1);

        C[C.length - 1][C.length - 2] = dX(C.length - 1) / 6;
        C[C.length - 1][C.length - 1] = (-1) * (dX(C.length) / 3 + dX(C.length + 1) / 3);
        freeB[C.length - 1] = (dY(C.length) / dX(C.length)) - (dY(C.length - 1) / dX(C.length - 1));
        return C;
    }

    // метод прогонки
    public void tridiagonalMethod() {
        double[][] C = getTridiagonalMatrix();
        double[] realC = new double[iNodes.length];
        double[] nodesC = new double[iNodes.length - 2];

        double alfa[] = new double[C.length + 1];
        double betta[] = new double[C.length + 1];

        alfa[1] = C[0][1]/C[0][0];
        betta[1] = -freeB[0]/C[0][0];

        for(int i = 2; i < C.length; i++) {
            alfa[i] = C[i - 1][i] / (C[i - 1][i - 1] - C[i - 1][i - 2] * alfa[i - 1]);
            betta[i] = (C[i - 1][i - 2] * betta[i - 1] - freeB[i - 1]) / (C[i - 1][i - 1] - C[i - 1][i - 2] * alfa[i - 1]);
        }

        betta[C.length] = (C[C.length - 1][C.length - 2] * betta[C.length - 1] - freeB[C.length - 1]) /
                (C[C.length - 1][C.length - 1] - C[C.length - 1][C.length - 2] * alfa[C.length - 1]);

        nodesC[C.length - 1] = betta[C.length];
        for(int i = C.length - 2; i >= 0; i--) {
            nodesC[i] = alfa[i + 1] * nodesC[i + 1] + betta[i + 1];
        }
        realC[0] = 0;
        realC[iNodes.length - 1] = 0;
        for (int i = 1; i < iNodes.length - 1; i++)
            realC[i] = nodesC[i - 1];
        this.C = realC;
    }

    public void setIndexD() {
        double d[] = new double[iNodes.length - 1];
        for (int i = 1; i < iNodes.length; i++)
            d[i - 1] = (this.C[i] - this.C[i - 1]) / dX(i);
        this.D = d;
    }

    public void setIndexB() {
        double b[] = new double[iNodes.length - 1];
        for (int i = 1; i < iNodes.length; i++)
            b[i - 1] = dX(i) * this.C[i] / 2 - dX(i) * dX(i) * this.D[i - 1] / 6 + dY(i) / dX(i);
        this.B = b;
    }

    //todo посчитаь значени сплайна в заданной точке
    public double getSpline(double x, int partOfInter) {
        double delta = x - iNodes[partOfInter];
        return iValues[partOfInter] + B[partOfInter] * delta + C[partOfInter] * delta * delta +
                D[partOfInter] / 6 * delta * delta * delta;
    }
}
