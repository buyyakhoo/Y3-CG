import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.util.Random;

class Lab1_660291 extends JPanel {
    public static void main(String[] args) {
        Lab1_660291 m = new Lab1_660291();

        JFrame f = new JFrame();
        f.add(m);
        f.setSize(600, 600);
        f.setTitle("First Swing");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        g.drawString("Hello", 40, 40);
        g.setColor(Color.BLUE);
        g.fillRect(130, 30, 100, 80);
        g.drawOval(30, 130, 50, 60);
        g.setColor(Color.RED);
        g.drawLine(0, 0, 200, 30);
        g.fillOval(130, 130, 50, 60);
        g.drawArc(30, 200, 40, 50, 90, 60);
        g.fillArc(30, 130, 40, 50, 180, 40);

        int baseX = 50, baseY = 50;
        g.setColor(Color.BLACK);

        Random rand = new Random();
        for (int n = 0; n < 10; n++) {
            int offsetX = n * 40;

            Color randomColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            g.setColor(randomColor);

            // Draw a simple 5-pointed star using plot()
            int centerX = baseX + offsetX + 20;
            int centerY = baseY + 300;
            int radiusOuter = 15;
            int radiusInner = 7;

            double angle = Math.PI / 2;
            for (int i = 0; i < 5; i++) {
                // Outer point
                int x1 = centerX + (int)(radiusOuter * Math.cos(angle));
                int y1 = centerY - (int)(radiusOuter * Math.sin(angle));
                // Inner point
                angle += Math.PI / 5;
                int x2 = centerX + (int)(radiusInner * Math.cos(angle));
                int y2 = centerY - (int)(radiusInner * Math.sin(angle));
                // Next outer point
                angle += Math.PI / 5;
                int x3 = centerX + (int)(radiusOuter * Math.cos(angle));
                int y3 = centerY - (int)(radiusOuter * Math.sin(angle));

                // Draw lines between points using plot()
                for (int t = 0; t <= 100; t++) {
                    double alpha = t / 100.0;
                    int px1 = (int) (x1 * (1 - alpha) + x2 * alpha);
                    int py1 = (int) (y1 * (1 - alpha) + y2 * alpha);
                    plot(g, px1, py1);

                    int px2 = (int) (x2 * (1 - alpha) + x3 * alpha);
                    int py2 = (int) (y2 * (1 - alpha) + y3 * alpha);
                    plot(g, px2, py2);
                }
            }
        }

    }

    private void plot(Graphics g, int x, int y) {
        g.fillRect(x, y, 1, 1);
    }
}