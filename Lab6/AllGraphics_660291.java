import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AllGraphics_660291 extends JPanel implements Runnable {

    public static void main(String[] args) {
        AllGraphics_660291 m = new AllGraphics_660291();

        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("Graphics");
        f.setSize(600, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        (new Thread(m)).start();
    }

    // double circleMove = 0;
    double squareRotate = 0;
    double scaleSize = 0;
    // boolean movingRight = true;

    double elapsedTotalTime = 0;
    // double squareY = 600;
    // boolean squareShouldStart = false;

    @Override
    public void run() {
        double lastTime = System.currentTimeMillis();
        double currentTime, elapsedTime;

        while (true) {
            currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - lastTime;
            lastTime = currentTime;

            elapsedTotalTime += elapsedTime;
            // TODO: update logics

            if (squareRotate <= Math.PI / 6) {
                squareRotate += 0.5 * elapsedTime / 1000.0;
                scaleSize = squareRotate / (Math.PI / 3);
            }

            // Display
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 600, 600);
        g2.setColor(Color.BLACK);

        // System.out.println(g2.getTransform());

        g2.setTransform(new AffineTransform(1, 0, 0, 1, 0, 0)); // Identity Matrix
        
        // 2. Scale x2 around top-left
        g2.scale(scaleSize, scaleSize);
        // 3. Translate by -50 on X and 50 on Y
        g2.translate(-50, 50);

        g2.rotate(squareRotate, 300, 300);
        g2.drawRect(200, 200, 200, 200);
        g2.rotate(-squareRotate, 300, 300);


        // g2.translate(ALLBITS, ABORT);

        
    }
    
}
