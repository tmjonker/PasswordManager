package org.example.tmjonker.PasswordManager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 */
public class LoginWindow implements DefaultWindow {

    Stage stage;

    public LoginWindow(Stage stage) {
        this.stage = stage;

        disableCloseMenuItem();

        TextField usernameField = new TextField();
        usernameField.setMaxWidth(100);
        usernameField.setPromptText("Username");
        usernameField.setFocusTraversable(false);

        TextField passwordField = new TextField();
        passwordField.setMaxWidth(100);
        passwordField.setPromptText("Password");
        passwordField.setFocusTraversable(false);

        HBox fieldBox = new HBox(10);
        fieldBox.getChildren().addAll(usernameField, passwordField);
        fieldBox.setAlignment(Pos.CENTER);
        fieldBox.setPadding(new Insets(15, 0, 0, 0));

        Button okButton = new Button("Ok");
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().add(okButton);
        buttonBox.setPadding(new Insets(2,0,10,0));
        buttonBox.setAlignment(Pos.CENTER);

        VBox mainVBox = new VBox(10);
        mainVBox.getChildren().addAll(fieldBox, buttonBox);

        borderPane.setCenter(mainVBox);

        prepareStage(stage, generateStructure(300, 148));
    }

    @Override
    public void onClose() {
        stage.close();
    }
}
