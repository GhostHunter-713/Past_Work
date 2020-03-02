package A6;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MoveCommand implements BlobCommand {

    ArrayList<Groupable> items;
    double lastX, lastY;

    MoveCommand(ArrayList<Groupable> items, double dx, double dy) {
        this.lastX = (-1)*dx;
        this.lastY = (-1)*dy;
        this.items = items;
    }

    /*
        The move that occurs when the redo button is pressed
     */
   public void doIt() {
       for(Groupable item : items) {
           item.move(lastX, lastY);
       }
       this.lastX = (-1)*lastX;
       this.lastY = (-1)*lastY;
    }

    /*
        Undo that occurs when the undo button is pressed
     */
    public void undoIt() {
        for(Groupable item : items) {
            item.move(lastX, lastY);
        }
       this.lastX = (-1)*lastX;
       this.lastY = (-1)*lastY;
    }

    /*
        The toString method for adding it to the list views
     */
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.00");
        String info = " Move: " + df.format(lastX) + "," + df.format(lastY);
        return info;
    }



}
