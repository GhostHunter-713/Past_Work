package A6;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.LinkedList;

public class Main extends Application {

    BlobView view;
    BlobController controller;
    BlobModel model;
    InteractionModel iModel;
    BorderPane border;
    VBox frame;
    VBox left;
    VBox right;
    Label clipboard;
    Label undo;
    Label redo;
    Button undoButton;
    Button redoButton;
    ListView undoList;
    ListView redoListredo;
    ListView redoList;

    ObservableList<String> itemsUndoDefault;
    ObservableList<String> itemsRedoDefault;

    ObservableList<String> itemsUndo;
    ObservableList<String> itemsRedo;



    @Override
    public void start(Stage primaryStage) {

        //StackPane rootr = new StackPane();

        // create MVC component/
        view = new BlobView(700,700);
        border = new BorderPane();

        controller = new BlobController();
        model = new BlobModel();
        iModel = new InteractionModel();
        iModel.setMain(this);

        // connect MVC components
        view.setModel(model);
        view.setInteractionModel(iModel);
        controller.setModel(model);
        controller.setInteractionModel(iModel);
        model.addSubscriber(view);
        iModel.addSubscriber(view);

        // set up event handling
        view.setOnMousePressed(controller::handlePressed);
        view.setOnMouseDragged(controller::handleDrag);
        view.setOnMouseReleased(controller::handleRelease);
        border.setOnKeyPressed(controller::handleKeyPressed);
        border.setOnKeyReleased(controller::handleKeyReleased);

        createLabels();
        createButtons();
        createLists();

        left = new VBox(undo, undoList, undoButton);
        left.setPrefSize(200, 600);
        left.setAlignment(Pos.CENTER);


        right = new VBox(redo, redoListredo, redoButton);
        right.setPrefSize(200, 600);
        right.setAlignment(Pos.CENTER);

        undoButton.setOnAction(u ->{
            iModel.undo();
            updateLists();
        });

        redoButton.setOnAction(r -> {
            iModel.redo();
            updateLists();
        });
        //bottom = new HBox(undoButton, redoButton);
        //bottom.setPrefSize(1200, 45);

        frame = new VBox(view, clipboard);
        frame.setPrefSize(800, 730);
        frame.setMaxSize(850,730);


        border.setLeft(left);
        border.setCenter(frame);
        border.setRight(right);
        //border.setBottom(bottom);


        Scene scene = new Scene(border);
        primaryStage.setTitle("Assignment 6 App");
        primaryStage.setScene(scene);
        primaryStage.show();
        view.requestFocus();
    }


    public static void main(String[] args) {
        launch(args);
    }

    /*
        Create the labels for Undo and Redo
     */
    public void createLabels() {
        undo = new Label("Undo Stack");
        undo.setPrefWidth(200);
        undo.setPrefHeight(25);
        undo.setAlignment(Pos.CENTER);

        redo = new Label("Redo Stack");
        redo.setPrefWidth(200);
        redo.setPrefHeight(25);
        redo.setMaxHeight(25);
        redo.setAlignment(Pos.CENTER);

        clipboard = new Label("Clipboard: Empty");
        clipboard.setPrefHeight(30);
    }

    /*
        Create the buttons for the window
     */
    public void createButtons() {
        undoButton = new Button("Undo!");
        undoButton.setPrefHeight(40);
        undoButton.setPrefWidth(100);


        redoButton = new Button("Redo!");
        redoButton.setPrefHeight(40);
        redoButton.setPrefWidth(100);

    }

    /*
        Create the ListsViews for undo and redo
     */
    public void createLists() {
        /*
        redoList = new ListView();
        redoList.setPrefWidth(100);
        redoList.prefHeight(700);
        redoList.minHeight(700);
        redoList.fixedCellSizeProperty();
*/
        redoListredo = new ListView();
        redoListredo.setPrefWidth(100);
        redoListredo.setPrefHeight(650);


        undoList = new ListView();
        undoList.setPrefWidth(100);
        undoList.setPrefHeight(650);
        undoList.fixedCellSizeProperty();

        itemsUndoDefault = FXCollections.observableArrayList ();
        String listSpaceU = "Empty";
        itemsUndoDefault.add(listSpaceU);
        for (int i=0; i < 50; i++){
            listSpaceU = "";
            itemsUndoDefault.add(listSpaceU);
        }
        undoList.setItems(itemsUndoDefault);

        itemsRedoDefault = FXCollections.observableArrayList ();
        String listSpaceR = "Empty";
        itemsRedoDefault.add(listSpaceR);
        for (int i=0; i < 50; i++){
            listSpaceR = "";
            itemsRedoDefault.add(listSpaceR);
        }
        redoListredo.setItems(itemsRedoDefault);
    }

    /*
        Adjusts the listViews with the new information
     */
    public void updateLists() {
        // Set the list for undo
        LinkedList<BlobCommand> undoList = iModel.getUndoList();
        itemsUndo = FXCollections.observableArrayList ();
        itemsUndo.clear();
        if(undoList.size() > 0) {
            for(BlobCommand b: undoList) {
                itemsUndo.add(b.toString());
            }
            this.undoList.setItems(itemsUndo);
        }
        else {
            this.undoList.setItems(itemsUndoDefault);
        }

        // Set the list for redo
        LinkedList<BlobCommand> redoList = iModel.getRedoList();
        itemsRedo = FXCollections.observableArrayList ();
        itemsRedo.clear();
        if(redoList.size() > 0) {
            for(BlobCommand b: redoList) {
                itemsRedo.add(b.toString());
            }
            this.redoListredo.setItems(itemsRedo);
        }
        else {
            this.redoListredo.setItems(itemsRedoDefault);
        }
    }

    public void updateLabel(boolean notEmpty) {
        if(notEmpty) {
            clipboard.setText("Clipboard: Has Groupable");
        }
        else {
            clipboard.setText("Clipboard: Empty");
        }
    }
}
