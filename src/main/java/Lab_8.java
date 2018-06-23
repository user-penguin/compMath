import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Lab_8 extends Lab {

    private int numberFunction;
    private double step;
    private double level;
    private double x[];
    private double y[];

    @Override
    public void fillFromFile(String path) {
        try(BufferedReader br = new BufferedReader(new FileReader(path)))
        {
            numberFunction = Integer.parseInt(br.readLine());

            int a = Integer.parseInt(br.readLine());
            int b = Integer.parseInt(br.readLine());

            step = Double.parseDouble(br.readLine());

            level = Double.parseDouble(br.readLine());

            int countStep = (int)((b - a) * (1 / step)) + 1;
            x = new double[countStep];
            y = new double[countStep];

            for (int i = 0; a + i * step <= b; i++)
                x[i] = a + i * step;
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void run() {

    }

    @Override
    public void print() {
        String str = "";

        str += "Отрезок [" + a + "; " + b + "]";

        System.out.println(str);
    }
}
