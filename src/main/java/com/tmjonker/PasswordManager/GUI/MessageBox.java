package com.tmjonker.PasswordManager.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MessageBox {

    public MessageBox(String message, String title) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(300);
        stage.setMinHeight(125);

        Label lbl = new Label();
        lbl.setText(message);
        lbl.setPadding(new Insets(0, 10, 0, 10));

        Button okButton = new Button();
        okButton.setText("OK");
        okButton.setOnAction(e -> stage.close());

        VBox pane = new VBox(20);
        pane.getChildren().addAll(lbl, okButton);
        pane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(pane);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
