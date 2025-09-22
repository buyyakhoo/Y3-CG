import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.util.Random;

class GraphicsSwing extends JPanel {
    public static void main(String[] args) {
        GraphicsSwing m = new GraphicsSwing();

        JFrame f = new JFrame();
        f.add(m);
        f.setSize(600, 600);
        f.setTitle("First Swing");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        // g.drawString("Hello", 40, 40);
        // g.setColor(Color.BLUE);
        // g.fillRect(130, 30, 100, 80);
        // g.drawOval(30, 130, 50, 60);
        // g.setColor(Color.RED);
        // g.drawLine(0, 0, 200, 30);
        // g.fillOval(130, 130, 50, 60);
        // g.drawArc(30, 200, 40, 50, 90, 60);
        // g.fillArc(30, 130, 40, 50, 180, 40);

        int baseX = 50, baseY = 50;
        g.setColor(Color.BLACK);

        Random rand = new Random();
        for (int n = 0; n < 10; n++) {
            int offsetX = n * 20;
            // Generate a random color for each shape
            Color randomColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            g.setColor(randomColor);

            // Left vertical line
            for (int i = 0; i < 7; i++) {
            plot(g, baseX + offsetX, baseY + i);
            }
            // Bottom curve
            plot(g, baseX + 1 + offsetX, baseY + 6);
            plot(g, baseX + 2 + offsetX, baseY + 6);
            // Right vertical line
            for (int i = 0; i < 7; i++) {
            plot(g, baseX + 3 + offsetX, baseY + i);
            }
        }

    }

    private void plot(Graphics g, int x, int y) {
        g.fillRect(x, y, 1, 1);
    }
}