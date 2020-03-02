package A6;

import java.util.ArrayList;

public class BlobClipboard {

    ArrayList<Groupable> heldBlobs;

    BlobClipboard(){
        this.heldBlobs = new ArrayList();
    }

    /*
        Save the clone of the selected blobs
     */
    public void setCopies(ArrayList<Groupable> blobs){
        heldBlobs = blobs;
    }

    /*
        checks to see if there are saved blob groups
     */
    public boolean hasBlobs() {
        if(heldBlobs.size() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    /*
        get the groups of blobs held by the clipboard
     */
    public ArrayList<Groupable> getHeldBlobs(){
        return heldBlobs;
    }

}
