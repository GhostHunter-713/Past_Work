package A6;

import java.text.DecimalFormat;

public class CreateCommand implements BlobCommand {

    Groupable item;

    BlobModel model;

    CreateCommand(Groupable item, BlobModel model) {
        this.item = item;
        this.model = model;
    }

    /*
        Re-add the the item to the list in the model
     */
    public void doIt() {
        model.add(item);
    }

    /*
        Removes the item from the list in the model
     */
    public void undoIt() {
        model.removeBlobs(item);
    }

    /*
        A string for printing the info in the list views
     */
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.00");
        String info = " Create: " + df.format(((Blob)item).x) + "," + df.format(((Blob)item).y);
        return info;
    }
}
