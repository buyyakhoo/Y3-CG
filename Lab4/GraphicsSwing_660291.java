import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

class GraphicsSwing_660291 extends JPanel {
    public static void main(String[] args) {
        GraphicsSwing_660291 m = new GraphicsSwing_660291();
        JFrame f = new JFrame();
        f.add(m);
        f.setSize(600, 600);
        f.setTitle("First Swing");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public void midpointCircle(Graphics g, int xc, int yc, int r) {
        int x = 0;
        int y = r;

        int d = 1 - r;

        while (x <= y) {
            plot(g, x + xc, y + yc, 3);
            plot(g, -x + xc, y + yc, 3);
            plot(g, x + xc, -y + yc, 3);
            plot(g, -x + xc, -y + yc, 3);
            plot(g, y + xc, x + yc, 3);
            plot(g, -y + xc, x + yc, 3);
            plot(g, y + xc, -x + yc, 3);
            plot(g, -y + xc, -x + yc, 3);

            x++;

            if (d >= 0) {
                y--;
                d = d - 2 * y;
            }

            d = d + 2 * x + 1;
        }
    }

    public void midpointEllipse( Graphics g, int xc, int yc, int a, int b) {
        int x, y, d;

        // start with region 1
        x = 0;
        y = b;
        d = Math.round(b*b - a*a*b + a*a/4);

        while (b*b*x <= a*a*y) {
            plot(g, x + xc, y + yc, 3);
            plot(g, x + xc, -y + yc, 3);
            plot(g, -x + xc, y + yc, 3);
            plot(g, -x + xc, -y + yc, 3);

            x++;

            if (d >= 0) {
                y--;
                d = d - 2*a*a*y;
            }

            d = d + 2*b*b*x + b*b;
        }

        // region 2
        x = a;
        y = 0;
        d = Math.round(a*a - b*b*a + b*b/4);

        while (b*b*x >= a*a*y) {
            plot(g, x + xc, y + yc, 2);
            plot(g, x + xc, -y + yc, 2);
            plot(g, -x + xc, y + yc, 2);
            plot(g, -x + xc, -y + yc, 2);

            y++;

            if (d >= 0) {
                x--;
                d = d - 2*b*b*x;
            }

            d = d + 2*a*a*y + b*b;
        }
    }

    public void paintComponent(Graphics g) {
        midpointCircle(g, 300, 300, 200);
        // midpointEllipse(g, 300, 300, 200, 100); // rx = 300, ry = 100
    }

    private void plot(Graphics g, int x, int y, int size) {
        g.fillRect(x, y, size, size);
    }
}