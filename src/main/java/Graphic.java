package lr6;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

public class Graphic extends JComponent {
    int x1[] = new int[101];
    int y1[] = new int[101];
    int x2[] = new int[101];
    int y2[] = new int[101];
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawLine(50, 300, 800, 300);
        g2d.drawLine(800, 300, 785, 290);
        g2d.drawLine(800, 300, 785, 310);
        g2d.drawLine(400, 50, 400, 600);
        g2d.drawLine(400, 50, 390, 65);
        g2d.drawLine(400, 50, 410, 65);
        g2d.setColor(Color.blue);
        g2d.drawPolyline(x1, y1, 101);
        g2d.setColor(Color.RED);
        g2d.drawPolyline(x2, y2, 101);
        //g2d.setColor(Color.blue);
        //g2d.drawPolyline(x1, y1, 101);
    }
    public void graph(Graphics g, int xI1[], int yI1[], int xI2[], int yI2[]) {
        x1 = xI1;
        y1 = yI1;
        x2 = xI2;
        y2 = yI2;
        paint(g);
    }
}
