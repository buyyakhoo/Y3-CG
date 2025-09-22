import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

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

    double circleMove = 0;
    double squareRotate = 0;
    boolean movingRight = true;

    double elapsedTotalTime = 0;
    double squareY = 600;
    boolean squareShouldStart = false;

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

            // Start moving the square after 3 seconds (3000 ms)
            if (elapsedTotalTime >= 3000) {
                squareShouldStart = true;
            }

            if (movingRight) {
                circleMove += 50.0 * elapsedTime / 1000.0;
                if (circleMove >= 500) {
                    movingRight = false;
                }
            } else {
                circleMove -= 50.0 * elapsedTime / 1000.0;
                if (circleMove <= 0) {
                    movingRight = true;
                }
            }

            // Change ms to s
            // Move 50 pixel/s
            // circleMove += 50.0 * elapsedTime / 1000.0;
            // Rotate 0.5 radians/secs
            squareRotate += 0.5 * elapsedTime / 1000.0;

            // Move square from bottom-left upward
            if (squareShouldStart) {
                squareY -= 100.0 * elapsedTime / 1000.0;
                if (squareY < 0) squareY = 0; 
            }

            // Display
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        // super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 600, 600);
        g2.setColor(Color.BLACK);


        g2.translate(circleMove, 0);
        g2.drawOval(0, 0, 100, 100);
        g2.translate(-circleMove, 0);
     
        g2.rotate(squareRotate, 300, 300);
        g2.drawRect(200, 200, 200, 200);
        g2.rotate(-squareRotate, 300, 300);
        // Draw moving square from bottom-left to top-left
        if (squareShouldStart) {
            g2.translate(0, squareY);
            g2.drawRect(0, 0, 50, 50);
            g2.translate(0, -squareY); // reset translation
        }
    }
    
}
