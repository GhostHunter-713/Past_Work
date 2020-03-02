
package A6;

import java.util.ArrayList;

public interface Groupable {
    
    boolean hasChildren();

    ArrayList<Groupable> getChildren();

    boolean contains(double x, double y);

    boolean isContained(double x1, double y1, double x2, double y2);

    double getLeft();

    double getRight();

    double getTop();

    double getBottom();

    void move(double dx, double dy);

    boolean isGroup();
}
