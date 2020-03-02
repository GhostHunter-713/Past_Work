package A6;

import java.util.ArrayList;

public class BlobGroup implements Groupable, Cloneable {

    ArrayList<Groupable> children;
    double left, top, right, bottom;

    public BlobGroup() {
        children = new ArrayList<>();
        left = Double.MAX_VALUE;
        top = Double.MAX_VALUE;
        right = 0;
        bottom = 0;
    }

    public void add(Groupable g) {
        children.add(g);
        left = Math.min(g.getLeft(), left);
        top = Math.min(g.getTop(), top);
        right = Math.max(g.getRight(), right);
        bottom = Math.max(g.getBottom(), bottom);
    }

    public void remove(Groupable g) {
        children.remove(g);
    }

    public boolean hasChildren() {
        return true;
    }

    public boolean contains(double x, double y) {
        return children.stream()
                .anyMatch(g -> g.contains(x, y));
    }

    public boolean isContained(double x1, double y1, double x2, double y2) {
        // if ALL members of the group are in the rect, return true
        return children.stream()
                .allMatch(g -> g.isContained(x1, y1, x2, y2));
    }

    public void move(double dx, double dy) {
        children.forEach(g -> g.move(dx, dy));
        left += dx;
        top += dy;
        right += dx;
        bottom += dy;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    public double getTop() {
        return top;
    }

    public double getBottom() {
        return bottom;
    }

    public ArrayList<Groupable> getChildren() {
        return children;
    }

    public Object clone() throws CloneNotSupportedException {

        BlobGroup blobs = (BlobGroup) super.clone();
        blobs.children = (ArrayList<Groupable>) childClone();


        return blobs;
    }

    public Object childClone() throws CloneNotSupportedException {
        ArrayList<Groupable> childClones = new ArrayList<>();
        children.forEach(g -> {
            try {
                childClones.add((Groupable)((Blob) g).clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        });
        return childClones;
    }


    public boolean isGroup() {
        return true;
    }
}
