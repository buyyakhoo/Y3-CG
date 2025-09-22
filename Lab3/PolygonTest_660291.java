import java.awt.*;
import javax.swing.*;

public class PolygonTest_660291 extends JPanel {
    public static void main(String[] args) {
        PolygonTest_660291 m = new PolygonTest_660291();
        JFrame f = new JFrame();
        f.add(m);
        f.setSize(600, 600);
        f.setTitle("PolygonTest");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    private void normalPolygon(Graphics g) {
        int xPoly[] = {150, 250, 325, 375, 400, 275, 100};
        int yPoly[] = {150, 100, 125, 225, 325, 375, 300};
        Polygon poly = new Polygon(xPoly, yPoly, xPoly.length);
        g.drawPolygon(poly);
    }

    private void triangulation1stPolygon(Graphics g) {
        int xPoly[] = {
            150, 250, 325, 
            150, 325,
            375, 
            150, 375,
            400, 
            100, 400,
            275, 100, 
            375, 100,
            150
        };
        int yPoly[] = {
            150, 100, 125, 
            150, 125,
            225, 
            150, 225,
            325, 
            300, 325,
            375, 300, 
            225, 300,
            150
        };
        Polygon poly = new Polygon(xPoly, yPoly, xPoly.length);
        g.drawPolygon(poly);
    }

    private void triangulation2ndPolygon(Graphics g) {
        int xPoly[] = {
            150, 250, 325, 375, 400, 275, 
            325, 275,
            375, 275,
            100,
            250, 100,
            325, 100, 
            150
        };
        int yPoly[] = {
            150, 100, 125, 225, 325, 375, 
            125, 375,
            225, 375,
            300, 
            100, 300,
            125, 300,
            150
        };
        Polygon poly = new Polygon(xPoly, yPoly, xPoly.length);
        g.drawPolygon(poly);
    }

    private void triangulation3rdPolygon(Graphics g) {
        int xPoly[] = {
            150, 250, 325, 375, 400,
            325, 400,
            250, 400,
            275, 
            250, 275,
            150, 275,
            100, 150
        };
        int yPoly[] = {
            150, 100, 125, 225, 325, 
            125, 325,
            100, 325,
            375, 
            100, 375,
            150, 375,
            300, 150
        };
        Polygon poly = new Polygon(xPoly, yPoly, xPoly.length);
        g.drawPolygon(poly);
    }

    private void swapPolygon(Graphics g) {
        // int xPoly[] = {150, 375, 325, 250, 400, 275, 100};
        // int yPoly[] = {150, 225, 125, 100, 325, 375, 300};
        // Polygon poly = new Polygon(xPoly, yPoly, xPoly.length);
        // g.drawPolygon(poly);

        int xPoly[] = {100, 375, 325, 250, 400, 275, 150};
        int yPoly[] = {300, 225, 125, 100, 325, 375, 150};
        Polygon poly = new Polygon(xPoly, yPoly, xPoly.length);
        g.drawPolygon(poly);
    }

    public void paintComponent(Graphics g) {
        // // normal polygon
        normalPolygon(g);

        // // do 3 triangulation
        // triangulation1stPolygon(g);
        // triangulation2ndPolygon(g);
        // triangulation3rdPolygon(g);

        // // swap position
        // swapPolygon(g);
    }
}
