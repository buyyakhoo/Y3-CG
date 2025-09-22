import java.awt.*;
import java.awt.image.*;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.*;

public class FloodFill_660291 extends JPanel {
    public static void main(String[] args) {
        FloodFill_660291 m = new FloodFill_660291();
        JFrame f = new JFrame();
        f.add(m);
        f.setSize(600, 600);
        f.setTitle("FloodFill");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        // flood-fill algorithm
        floodFillAlgorithm(g);
    }

    private void floodFillAlgorithm(Graphics g) {
        BufferedImage buffer = new BufferedImage(601, 601, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffer.createGraphics();

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 600, 600);

        g2.setColor(Color.BLACK);
        int xPoly[] = {150, 250, 325, 375, 400, 275, 100};
        int yPoly[] = {150, 100, 125, 225, 325, 375, 300};
        Polygon poly = new Polygon(xPoly, yPoly, xPoly.length);
        g2.drawPolygon(poly);

        buffer = floodFill(buffer, 200, 150, Color.WHITE, Color.GREEN);
        g.drawImage(buffer, 0, 0, null);
    }

    public BufferedImage floodFill(BufferedImage m, int x, int y, Color target_color, Color replacement_color) {
        int targetRGB = target_color.getRGB();
        int replacementRGB = replacement_color.getRGB();
  
        if (x < 0 || x >= m.getWidth()-1 || y < 0 || y >= m.getHeight()-1 || m.getRGB(x, y) != targetRGB || targetRGB == replacementRGB) return m;

        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(x, y));

        while (!queue.isEmpty()) {
            Point p = queue.poll();
            int currentX = p.x;
            int currentY = p.y;

            if (currentX > 0 && currentX < m.getWidth()-1 && currentY > 0 && currentY < m.getHeight()-1 && m.getRGB(currentX, currentY) == targetRGB) {
                m.setRGB(currentX, currentY, replacementRGB);

                if (m.getRGB(currentX + 1, currentY) == targetRGB) {
                    queue.add(new Point(currentX + 1, currentY)); 
                }

                if (m.getRGB(currentX - 1, currentY) == targetRGB) {
                    queue.add(new Point(currentX - 1, currentY));
                }

                if (m.getRGB(currentX, currentY + 1) == targetRGB) {
                    queue.add(new Point(currentX, currentY + 1));
                }

                if (m.getRGB(currentX, currentY - 1) == targetRGB) {
                    queue.add(new Point(currentX, currentY - 1)); 
                }
            }
        }
        
        return m;
    }
}

