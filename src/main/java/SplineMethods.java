import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SplineMethods {
    private double[] iNodes;
    private double[] iValues;

    private String typeOfFunct;
    private double tableStep;
    private double buildStep;
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

    public void setTableStep(double step) {
        tableStep = step;
    }

    public void setBuildStep(double step) {
        buildStep = step;
    }

    public void setLeftBorder(double border) {
        leftBorder = border;
    }

    public void setRightBorder(double border) {
        rightBorder = border;
    }

    public void setIndexesC(double[] C) {
        this.C = C;
    }

    public void setFreeB(double[] freeB) {
        this.freeB = freeB;
    }

    public double getTableStep() {
        return tableStep;
    }

    public double getBuildStep() {
        return buildStep;
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

            setTableStep(Double.parseDouble(br.readLine()));
            setBuildStep(Double.parseDouble(br.readLine()));
            setLeftBorder(Double.parseDouble(br.readLine()));
            setRightBorder(Double.parseDouble(br.readLine()));

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void calcSplineArguments() {
        int size = (int) ((rightBorder - leftBorder) / tableStep) + 1;
        iNodes = new double[size];
        int i = 0;
        double currentNode = leftBorder;

        while (currentNode - MatrixMath.E <= rightBorder) {
            iNodes[i] = currentNode;
            currentNode += tableStep;
            i++;
        }
    }

    public void calcSplineValues() {
        iValues = new double[iNodes.length];
        for (int i = 0; i < iNodes.length; i++)
            this.iValues[i] = (new InterpolMethods()).calcValueAvailFunction(this.typeOfFunct, this.iNodes[i]);
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
            C[i][i] = (-1) * (dX(i) / 3 + dX(i + 1) / 3); // минус перед коэффициентом
            C[i][i + 1] = dX(i + 1) / 6;
            freeB[i] = (dY(i + 1) / dX(i + 1)) - (dY(i) / dX(i));
        }

        C[0][0] = (-1) * (dX(1) / 3 + dX(2) / 3); //минус перед кэфом
        C[0][1] = (dX(2)) / 6;
        freeB[0] = dY(2) / dX(2) - dY(1) / dX(1);

        C[C.length - 1][C.length - 2] = (-1) * dX(C.length - 1) / 6;
        C[C.length - 1][C.length - 1] = (dX(C.length) / 3 + dX(C.length + 1) / 3);
        freeB[C.length - 1] = (dY(C.length) / dX(C.length)) - (dY(C.length - 1) / dX(C.length - 1));

        return C;
    }

    // метод прогонки
    public double[] tridiagonalMethod(double[][] sourceMatrix) {
        double[] realC = new double[sourceMatrix.length + 2];
        double[] nodesC = new double[sourceMatrix.length + 1];

        double alfa[] = new double[sourceMatrix.length + 2];
        double betta[] = new double[sourceMatrix.length + 2];

        alfa[1] = 0;
        betta[1] = 0;

        for(int i = 2; i < sourceMatrix.length; i++) {
            alfa[i] = sourceMatrix[i - 1][i] / (sourceMatrix[i - 1][i - 1] + sourceMatrix[i - 1][i - 2] * alfa[i - 1]);
            betta[i] = (sourceMatrix[i - 1][i - 2] * betta[i - 1] - freeB[i - 1]) /
                    (sourceMatrix[i - 1][i - 1] + sourceMatrix[i - 1][i - 2] * alfa[i - 1]);
        }

        betta[sourceMatrix.length] = (sourceMatrix[sourceMatrix.length - 1][sourceMatrix.length - 2] *
                betta[sourceMatrix.length - 1] - freeB[sourceMatrix.length - 1]) /
                (sourceMatrix[sourceMatrix.length - 1][sourceMatrix.length - 1] -
                 sourceMatrix[sourceMatrix.length - 1][sourceMatrix.length - 2] * alfa[sourceMatrix.length - 1]);

        nodesC[sourceMatrix.length - 1] = betta[sourceMatrix.length];
        nodesC[nodesC.length - 1] = 0;


        realC[sourceMatrix.length] = 0;
        alfa[sourceMatrix.length] = 0;
        betta[sourceMatrix.length] = 0;

        for(int i = sourceMatrix.length - 1; i >= 0; i--) {
            nodesC[i] = alfa[i + 1] * nodesC[i + 1] + betta[i + 1];
        }
        realC[0] = 0;

        for (int i = 1; i < sourceMatrix.length - 1; i++)
            realC[i] = nodesC[i];

        return realC;
    }

    public void setIndexD() {
        double d[] = new double[iNodes.length];
        for (int i = 1; i < iNodes.length; i++)
            d[i] = (this.C[i] - this.C[i - 1]) / dX(i);
        this.D = d;
    }

    public void setIndexB() {
        double b[] = new double[iNodes.length];
        for (int i = 1; i < iNodes.length; i++)
            b[i] = dX(i) * this.C[i] / 2 - dX(i) * dX(i) * this.D[i]  / 6 + dY(i) / dX(i);
        this.B = b;
    }

    //todo посчитаь значени сплайна в заданной точке
    public double getSpline(double x, int partOfInter) {
        double delta = x - iNodes[partOfInter];
        return iValues[partOfInter] + B[partOfInter] * delta + C[partOfInter] / 2 * delta * delta +
                D[partOfInter] / 6 * delta * delta * delta;
    }
}
