package A6;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BlobModel {
    ArrayList<BlobModelListener> subscribers;
    ArrayList<Groupable> items;

    public BlobModel() {
        subscribers = new ArrayList<>();
        items = new ArrayList<>();
    }

    public Blob createBlob(double x, double y) {
        Blob b = new Blob(x, y);
        items.add(b);
        notifySubscribers();
        return b;
    }

    /*
        Adds the blobs from the clipboard
     */
    public void add( Groupable g) {
        items.add(g);
        notifySubscribers();
    }

    /*
        Removes the blobs in the clipboard
     */
    public void removeBlobs (Groupable group) {
        // System.out.println("Current items in the list " + items.size() + " before deletion.");
        boolean removed = items.remove(group);
        if (removed) {
            System.out.println("Item removed successfully!");
        }
        else {
            System.out.println("Item was not removed.");
        }
        // System.out.println("Current items in the list " + items.size() + " after deletion");
        notifySubscribers();
    }

    public boolean checkHit(double x, double y) {
        return items.stream()
                .anyMatch(b -> b.contains(x, y));
    }

    public Groupable find(double x, double y) {
        Optional<Groupable> result = items.stream()
                .filter(g -> g.contains(x, y))
                .reduce((g1, g2) -> g2);
        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

    public List<Groupable> findInRubberband(double x1, double y1, double x2, double y2) {
        return items.stream()
                .filter(g -> g.isContained(x1, y1, x2, y2))
                .collect(Collectors.toList());
    }

    public void move(List<Groupable> moveGroup, double dX, double dY) {
        moveGroup.forEach(g -> g.move(dX, dY));
        notifySubscribers();
    }

    public BlobGroup createGroup(ArrayList<Groupable> items) {
        this.items.removeAll(items);
        BlobGroup newGroup = new BlobGroup();
        items.forEach(g -> newGroup.add(g));
        this.items.add(newGroup);
        notifySubscribers();
        return newGroup;
    }

    public ArrayList<Groupable> unGroup(Groupable oldGroup) {
        oldGroup.getChildren().forEach(g -> items.add(g));
        items.remove(oldGroup);
        notifySubscribers();
        return oldGroup.getChildren();
    }

    public void addSubscriber(BlobModelListener aSub) {
        subscribers.add(aSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }
}
