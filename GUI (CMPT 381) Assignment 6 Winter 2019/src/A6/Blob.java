package A6;

import java.util.ArrayList;

public class Blob implements Groupable, Cloneable {
    double x, y, r;

    public Blob(double newX, double newY) {
        x = newX;
        y = newY;
        r = 30;
    }

    public void move(double dX, double dY) {
        x += dX;
        y += dY;
    }

    private double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public boolean contains(double sx, double sy) {
        return r > dist(sx, sy, x, y);
    }

    public boolean isContained(double x1, double y1, double x2, double y2) {
        return x - r >= x1 && y - r >= y1 && x + r <= x2 && y + r <= y2;
    }

    public boolean hasChildren() {
        return false;
    }

    public double getLeft() {
        return x - r;
    }

    public double getRight() {
        return x + r;
    }

    public double getTop() {
        return y - r;
    }

    public double getBottom() {
        return y + r;
    }

    public ArrayList<Groupable> getChildren() {
        return null;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean isGroup() {
        return false;
    }
}
