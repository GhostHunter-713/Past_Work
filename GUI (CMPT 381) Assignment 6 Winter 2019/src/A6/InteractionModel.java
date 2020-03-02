package A6;

import java.util.*;

public class InteractionModel {
    ArrayList<Groupable> selection;
    ArrayList<BlobModelListener> subscribers;
    RubberRectangle rubber;
    boolean controlDown;
    double viewWidth, viewHeight;

    LinkedList<BlobCommand> undoList;
    LinkedList<BlobCommand> redoList;

    BlobClipboard heldSelection;

    Main main;

    public InteractionModel() {
        subscribers = new ArrayList<>();
        selection = new ArrayList<>();
        rubber = null;
        controlDown = false;
        heldSelection = new BlobClipboard();

        undoList = new LinkedList<>();
        redoList = new LinkedList<>();
    }

    public void recordViewSize(double w, double h) {
        viewWidth = w;
        viewHeight = h;
    }

    public void setControl(boolean isDown) {
        controlDown = isDown;
        notifySubscribers();
    }

    public void clearSelection() {
        selection.clear();
        notifySubscribers();
    }

    public void setSelection(Groupable g) {
        selection.clear();
        selection.add(g);
        notifySubscribers();
    }

    public void setSelection(ArrayList<Groupable> group) {
        selection = group;
        notifySubscribers();
    }

    public boolean isSelected(Groupable g) {
        return selection.contains(g);
    }

    public void createRubber(double x1, double y1) {
        rubber = new RubberRectangle(x1, y1);
    }

    public void setRubberEnd(double x2, double y2) {
        rubber.updateCoords(x2, y2);
        notifySubscribers();
    }

    public boolean hasRubberband() {
        return (rubber != null);
    }

    public void deleteRubber() {
        rubber = null;
        notifySubscribers();
    }

    public void addSubtractSelection(Groupable g) {
        if (selection.contains(g)) {
            selection.remove(g);
        } else {
            selection.add(g);
        }
        notifySubscribers();
    }

    public void addSubtractSelection(List<Groupable> set) {
        set.forEach(g -> addSubtractSelection(g));
    }

    public void addSubscriber (BlobModelListener aSub) {
        subscribers.add(aSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }

    /*
        Create a copy of selected Group of blobs
     */
    public void saveBlobGroup() throws CloneNotSupportedException {
        if(selection.size() > 0) {
            ArrayList<Groupable> copy = new ArrayList<>();
            for (Groupable b : selection) {
                if (b.isGroup()) {
                    copy.add((Groupable) (((BlobGroup) b).clone()));
                }
                else {
                    copy.add((Groupable)((Blob) b).clone());
                }

            }
            System.out.println("We copied " + copy.size() + " blobs and blob groups");
            heldSelection.setCopies(copy);
        }
        else {
            System.out.println("Nothing selected to copy!");
        }
    }

    /*
        Saves a history of the move operation
     */
    public void moveGroup(ArrayList<Groupable> items, double dx, double dy) {
        ArrayList<Groupable> list = new ArrayList<>();
        for(Groupable g: items) {
            list.add(g);
        }
        MoveCommand moveTo = new MoveCommand(list, dx, dy);
        undoList.push(moveTo);
        this.main.updateLists();
    }

    /*
        Save a creation of a Blob
     */
    public void createGroup(Groupable item, BlobModel model) {
        CreateCommand createdBlob = new CreateCommand(item, model);
        undoList.push(createdBlob);
        this.main.updateLists();
    }

    /*
        Undo the first action in the undo list
     */
    public void undo() {
        if(undoList.size() == 0) {
            return;
        }
        BlobCommand command = undoList.pop();
        command.undoIt();
        redoList.push(command);
        notifySubscribers();
    }

    /*
        Redo the first action in the redo list
     */
    public void redo() {
        if(redoList.size() == 0) {
            return;
        }
        BlobCommand command = redoList.pop();
        command.doIt();
        undoList.push(command);
        notifySubscribers();
    }

    /*
        Get the list of undo items
     */
    public LinkedList<BlobCommand> getUndoList() {
        return undoList;
    }

    /*
        Get the list of redo items
     */
    public LinkedList<BlobCommand> getRedoList() {
        return redoList;
    }

    /*
        Set a reference to Main
     */
    public void setMain(Main m) {
        this.main = m;
    }

    public void updateClipboardLabel(boolean notEmpty) {
        main.updateLabel(notEmpty);
    }
}
