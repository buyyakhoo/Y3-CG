import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.util.Random;

class ExperimentDrawLine extends JPanel {
    static int w = 200, h = 900;
    public static void main(String[] args) {
        ExperimentDrawLine m = new ExperimentDrawLine();

        JFrame f = new JFrame();
        f.add(m);
        f.setSize(w+20, h+40);
        f.setTitle("First Swing");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public void paintComponent(Graphics g) {

        double m = (double)(h-1)/(w-1);
        double c = (double)((w-1)-(h-1))/(w-1);

        // int y = m*x + c;

        for (int i=0; i<w; i++) {
            plot(g, i, (int)(m*i + c + 0.5));
        }
    }

    private void plot(Graphics g, int x, int y) {
        g.fillRect(x, y, 1, 1);
    }
}