package com.tmjonker.PasswordManager.GUI;

import com.tmjonker.PasswordManager.User.UserHandler;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 *
 */
public class LoginWindow extends DefaultWindow {

    TextField usernameField;
    PasswordField passwordField;

    public LoginWindow(Stage stage) {

        disableCloseMenuItem();

        Text title = new Text("Login");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        VBox titleBox = new VBox(title);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(10, 0 ,0, 0));

        usernameField = new TextField();
        usernameField.setMaxWidth(100);
        usernameField.setPromptText("Username");
        usernameField.setFocusTraversable(true);

        passwordField = new PasswordField();
        passwordField.setMaxWidth(100);
        passwordField.setPromptText("Password");
        passwordField.setFocusTraversable(true);

        HBox fieldBox = new HBox(10);
        fieldBox.getChildren().addAll(usernameField, passwordField);
        fieldBox.setAlignment(Pos.CENTER);
        fieldBox.setPadding(new Insets(0, 0, 0, 0));

        Button okButton = new Button("Ok");
        Platform.runLater(() -> okButton.requestFocus());
        okButton.setOnAction(e -> onOkButtonClick());

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().add(okButton);
        buttonBox.setPadding(new Insets(2,0,10,0));
        buttonBox.setAlignment(Pos.CENTER);

        VBox mainVBox = new VBox(10);
        mainVBox.getChildren().addAll(titleBox, fieldBox, buttonBox);

        setCenter(mainVBox);

        prepareStage(stage, generateStructure(300, 170));
    }

    private void onOkButtonClick() {

        UserHandler userHandler = new UserHandler();
        if (checkUserInput(userHandler.checkExists(usernameField.getText()))) {
            try {
                userHandler.validateReturningUser(usernameField.getText(), passwordField.getText().getBytes());
                clearFields();

            } catch (GeneralSecurityException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /*
    checkUserInput:
    Checks if user input contains any blank spaces and checks to see if the username that the user input has already
    been created.
     */
    private boolean checkUserInput(Boolean exists) {

        if (usernameField.getText().contains(" ")
                || passwordField.getText().contains(" ")) {
            clearFields();
            new MessageBox("No spaces are allowed.", "Error");
            return false;
        } else if (!exists){
            clearFields();
            new MessageBox("User " + usernameField.getText() + " does not exist.", "Error");
            return false;
        }else {
            return true;
        }
    }

    private void clearFields() {

        usernameField.clear();
        passwordField.clear();
    }
}
