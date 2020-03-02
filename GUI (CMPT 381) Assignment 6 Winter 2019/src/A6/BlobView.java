package A6;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class BlobView extends Pane implements BlobModelListener {
    Canvas myCanvas;
    GraphicsContext gc;
    double width, height;
    BlobModel model;
    InteractionModel iModel;

    public BlobView(int newW, int newH) {
        width = newW;
        height = newH;
        setStyle("-fx-background-color: mediumseagreen");
        myCanvas = new Canvas(width, height);
        gc = myCanvas.getGraphicsContext2D();
        getChildren().add(myCanvas);
    }

    public void setModel(BlobModel newModel) {
        model = newModel;
    }

    public void setInteractionModel(InteractionModel newModel) {
        iModel = newModel;
        iModel.recordViewSize(width,height);
    }

    public void draw() {
        gc.clearRect(0, 0, width, height);

        // draw CTRL
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font(24));
        gc.fillText("Control: " + iModel.controlDown, 10, 30);

        // draw rubberband if exists
        if (iModel.hasRubberband()) {
            gc.setFill(Color.YELLOW);
            gc.fillRect(iModel.rubber.left, iModel.rubber.top, iModel.rubber.width, iModel.rubber.height);
        }

        // draw model items
        model.items.forEach(g -> drawGroup(g, iModel.isSelected(g)));
    }

    private void drawGroup(Groupable g, boolean selected) {
        if (g.hasChildren()) {
            // draw each child
            g.getChildren().forEach(child -> drawGroup(child, selected));
            gc.setStroke(Color.BLACK);
            // draw bounding box
            gc.strokeRect(g.getLeft(), g.getTop(),
                    g.getRight() - g.getLeft(), g.getBottom() - g.getTop());
        } else {
            // draw single blob
            if (selected) {
                gc.setFill(Color.HOTPINK);
            } else {
                gc.setFill(Color.PURPLE);
            }
            gc.fillOval(g.getLeft(), g.getTop(),
                    g.getRight() - g.getLeft(), g.getBottom() - g.getTop());
            gc.setStroke(Color.BLACK);
            gc.strokeOval(g.getLeft(), g.getTop(),
                    g.getRight() - g.getLeft(), g.getBottom() - g.getTop());
        }
    }

    public void layoutChildren() {
        width = this.getWidth();
        height = this.getHeight();
        myCanvas.setWidth(width);
        myCanvas.setHeight(height);
        draw();
    }

    public void modelChanged() {
        draw();
    }
}
