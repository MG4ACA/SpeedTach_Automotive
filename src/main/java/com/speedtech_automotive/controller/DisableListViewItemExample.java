package com.speedtech_automotive.controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class DisableListViewItemExample extends Application {

    public void start(Stage primaryStage) {
        ListView<String> listView = new ListView<>();

        // Add items to the ListView
        listView.getItems().addAll("Item 1", "Item 2", "Item 3", "Item 4");

        // Set a custom cell factory
        listView.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setDisable(false); // Enable empty cells
                } else {
                    setText(item);
                    // Disable a specific item (for example, "Item 2")
                    if (item.equals("Item 2")) {
                        setDisable(true);
                        setStyle("-fx-opacity: 0.5; -fx-text-fill: gray;"); // Apply style to disabled item
                    } else {
                        setDisable(false);
                    }
                }
            }
        });

        primaryStage.setScene(new Scene(listView, 200, 200));
        primaryStage.setTitle("Disabled ListView Item");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
