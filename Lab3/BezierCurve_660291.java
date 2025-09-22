import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

class BezierCurve_660291 extends JPanel {
    public static void main(String[] args) {
        BezierCurve_660291 m = new BezierCurve_660291();
        JFrame f = new JFrame();
        f.add(m);
        f.setSize(600, 600);
        f.setTitle("First Swing");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public void bezierCurve(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        g.setColor(Color.RED);
        plot(g, x1, y1, 6);
        plot(g, x2, y2, 6);
        plot(g, x3, y3, 6);
        plot(g, x4, y4, 6);
        
        for (double t=0; t<=1; t+=0.001) {
            int x = (int) (Math.pow(1-t, 3) * x1 + 
                    3 * t * Math.pow(1-t, 2) * x2 + 
                    3 * Math.pow(t, 2) * (1-t) * x3 + 
                    Math.pow(t, 3) * x4);

            int y = (int) (Math.pow(1-t, 3) * y1 + 
                    3 * t * Math.pow(1-t, 2) * y2 + 
                    3 * Math.pow(t, 2) * (1-t) * y3 + 
                    Math.pow(t, 3) * y4);

            g.setColor(Color.BLACK);
            plot(g, x, y, 3);
        }
    }

    public void paintComponent(Graphics g) {
        // ex.1
        bezierCurve(g, 200, 500, 100, 200, 500, 200, 400, 500);
        // bezierCurve(g, 100, 500, 200, 200, 500, 200, 400, 500);
        // bezierCurve(g, 100, 200, 400, 500, 500, 500, 200, 200);
        // bezierCurve(g, 400, 500, 200, 200, 500, 500, 300, 500);
    }

    private void plot(Graphics g, int x, int y, int size) {
        g.fillRect(x, y, size, size);
    }
}