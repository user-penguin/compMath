import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Graphic extends JComponent {
    int s = 0;
    int n = 0;
    int y[] = new int[s];
    int x[] = new int[s];
    int tX[] = new int[n];
    int tY[] = new int[n];
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.blue);
        for(int i = 0; i < n; i++) {
            g2d.drawLine(tX[i], tY[i], tX[i], tY[i]);
        }
        g2d.setColor(Color.BLACK);
        g2d.drawLine(50, 300, 800, 300);
        g2d.drawLine(800, 300, 785, 290);
        g2d.drawLine(800, 300, 785, 310);
        g2d.drawLine(400, 50, 400, 600);
        g2d.drawLine(400, 50, 390, 65);
        g2d.drawLine(400, 50, 410, 65);
        g2d.setColor(Color.RED);
        g2d.drawPolyline(x, y, s);
    }
    public void graph(Graphics g, int xI[], int yI[], int tochX[], int tochY[]) {
        x = xI;
        y = yI;
        tX = tochX;
        tY = tochY;
        n = tochX.length;
        for(int i = 0; i < x.length; i++) {
            if(xI[i] != 0) {
                s++;
            }
        }
        paint(g);
    }
}
