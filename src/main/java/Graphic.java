import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class Graphic extends JComponent {

    private JFrame frame = new JFrame();

    private int countLines;

    private int x[][] = new int[100][];
    private int y[][] = new int[100][];

    public Graphic() {
        frame.setTitle("Графики функций");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setBackground(Color.WHITE);
        frame.setLocationRelativeTo(null);
        frame.add(this);
        frame.setVisible(true);
    }

    public void paint() {
        paint(this.getGraphics());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON); //сглаживание
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        paintXY(g2d);
        paintLines(g2d);
    }

    private void paintXY(Graphics2D g2d) {
        int padding = 30;
        g2d.drawLine(padding, getHeight() / 2, getWidth() - padding, getHeight() / 2);
        g2d.drawLine(getWidth() - padding, getHeight() / 2, getWidth() - padding - 15, getHeight() / 2 - 10);
        g2d.drawLine(getWidth() - padding, getHeight() / 2, getWidth() - padding - 15, getHeight() / 2 + 10);
        g2d.drawLine(getWidth() / 2, padding, getWidth() / 2, getHeight() - padding);
        g2d.drawLine(getWidth() / 2, padding, getWidth() / 2 - 10, padding + 15);
        g2d.drawLine(getWidth() / 2, padding, getWidth() / 2 + 10, padding + 15);
    }

    private void paintLines(Graphics2D g2d) {
        for (int i = 0; i < countLines; i++) {
            g2d.setStroke(new BasicStroke(3.0f));
            if (i == 0)
                g2d.setColor(Color.blue);
            if (i == 1)
                g2d.setColor(Color.red);
            if (i == 2)
                g2d.setColor(Color.green);

            int[] x = new int[this.x[i].length];
            int[] y = new int[this.x[i].length];

            for (int j = 0; j < x.length; j++){
                x[j] = this.x[i][j] + getWidth() / 2;
                y[j] = - this.y[i][j] + getHeight() / 2;
            }
            g2d.drawPolyline(x, y, x.length);
        }
    }

    public void addLine(int[] x, int[] y) {
        this.x[countLines] = x;
        this.y[countLines] = y;
        countLines++;
    }
}
