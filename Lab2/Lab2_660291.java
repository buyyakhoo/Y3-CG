import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

class Lab2_660291 extends JPanel {
    public static void main(String[] args) {
        Lab2_660291 m = new Lab2_660291();
        JFrame f = new JFrame();
        f.add(m);
        f.setSize(600, 600);
        f.setTitle("First Swing");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    private void swapValueArr(int[] arr) {
        int tmp;
        tmp = arr[0];
        arr[0] = arr[1];
        arr[1] = tmp;
    }

    public void naiveLine(Graphics g, int x1, int y1, int x2, int y2) {
        int x_pair[], y_pair[];
        x_pair = new int[]{x1, x2};
        y_pair = new int[]{y1, y2};

        if (x1 > x2) {
            swapValueArr(x_pair);
            swapValueArr(y_pair);
        }
        
        double dx = findDifferential(x_pair[1], x_pair[0]);
        double dy = findDifferential(y_pair[1], y_pair[0]);

        double m = calculateSlope(dx, dy);
        double b = calculateIntercept(x_pair[0], y_pair[0], m);

        for (int x = x_pair[0]; x <= x_pair[1]; x++) {
            int y = (int)(m * x + b);
            plot(g, x, y, 5);
        }
    }

    private double calculateIntercept(double x, double y, double m) {
        return y - m * x;
    }

    public void ddaLine(Graphics g, int x1, int y1, int x2, int y2) {
        int x_pair[], y_pair[];
        x_pair = new int[]{x1, x2};
        y_pair = new int[]{y1, y2};

        if (x1 > x2) {
            swapValueArr(x_pair);
            swapValueArr(y_pair);
        }
        
        double dx = findDifferential(x_pair[1], x_pair[0]);
        double dy = findDifferential(y_pair[1], y_pair[0]);

        double m = calculateSlope(dx, dy);
        
        double y = y_pair[0];
        double x = x_pair[0];

        drawDDA(g, x, y, m, x_pair[0], x_pair[1], y_pair[0], y_pair[1]);

    }

    private double findDifferential(int a, int b) {
        return a - b;
    }

    private double calculateSlope(double dx, double dy) {
        return dy / dx;
    }

    private void drawDDA(Graphics g, double x, double y, double m, int x1, int x2, int y1, int y2) {
        if (m <= 1 && m >= 0) {
            for (x = x1; x <= x2; x++) {
                plot(g, (int)x, (int)y, 5);
                y += m;
            }
        } else if (m <= -1) {
            for (x = x2; x >= x1; x--) {
                plot(g, (int)x, (int)y, 5);
                y += m;
            }
        } else if (m > 1) {
            for (y=y1; y<=y2; y++) {
                plot(g, (int)x, (int)y, 5);
                x += 1 / m;
            }
        } else {
            for (y=y2; y>=y1; y--) {
                plot(g, (int)x, (int)y, 5);
                x += 1 / m;
            }
        }
    }

    public void bresenhamLine(Graphics g, int x1, int y1, int x2, int y2) {
        double dx = Math.abs(x2 - x1);
        double dy = Math.abs(y2 - y1);

        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;

        boolean isSwap = false;

        if (dy > dx) {
            double tmp = dy;
            dy = dx;
            dx = tmp;
            isSwap = true;
        }

        double D = 2 * dy - dx;
        double x = x1;
        double y = y1;

        for (int i=1; i<=dx; i++) {
            plot(g, (int)x, (int)y, 5);
            if (D >= 0) {
                if (isSwap) x += sx;
                else y += sy;

                D -= 2 * dx;
            }

            if (isSwap) y += sy;
            else x += sx;

            D += 2 * dy;
        }
    }

    public void paintComponent(Graphics g) {
        // displayNaiveLine(g);
        // displayDDALine(g);
        displayBresenhamLine(g);
    }

    private void displayNaiveLine(Graphics g) {
        naiveLine(g, 100, 100, 400, 200);
        naiveLine(g, 400, 300, 100, 200);
        naiveLine(g, 100, 100, 200, 400);
    }

    private void displayDDALine(Graphics g) {
        ddaLine(g, 100, 100, 400, 200);
        ddaLine(g, 400, 300, 100, 200);
        ddaLine(g, 100, 100, 200, 400);
    }

    private void displayBresenhamLine(Graphics g) {
        bresenhamLine(g, 100, 100, 400, 200);
        bresenhamLine(g, 400, 300, 100, 200);
        bresenhamLine(g, 100, 100, 200, 400);
    }

    private void plot(Graphics g, int x, int y, int size) {
        g.fillRect(x, y, size, size);
    }
}