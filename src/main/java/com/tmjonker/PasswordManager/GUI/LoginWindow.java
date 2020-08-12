package com.tmjonker.PasswordManager.GUI;

import com.tmjonker.PasswordManager.Users.UserHandler;
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

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.Normalizer;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 */
public class LoginWindow extends DefaultWindow {

    TextField usernameField;
    PasswordField passwordField;
    Button okButton = implementOkButton();
    boolean isClosing;

    public LoginWindow(Stage stage) {

        disableCloseMenuItem(true);
        disableLogOutMenuItem(true);

        Text title = new Text("Login");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        VBox titleBox = new VBox(title);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(10, 0, 0, 0));

        usernameField = new TextField();
        usernameField.setMaxWidth(100);
        usernameField.setPromptText("Username");
        usernameField.setFocusTraversable(true);
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty());
        });

        passwordField = new PasswordField();
        passwordField.setMaxWidth(100);
        passwordField.setPromptText("Password");
        passwordField.setFocusTraversable(true);


        HBox fieldBox = new HBox(10);
        fieldBox.getChildren().addAll(usernameField, passwordField);
        fieldBox.setAlignment(Pos.CENTER);
        fieldBox.setPadding(new Insets(0, 0, 0, 0));

        okButton.setDisable(true);
        okButton.setOnAction(e -> onOkButtonClick());
        okButton.setOnMouseEntered(e -> setStatusBarText("Login as " + usernameField.getText()));
        okButton.setOnMouseExited(e -> setStatusBarText(""));

        addToButtonBox(okButton);
        setPaddingButtonBox(2, 0, 10, 0);
        setAlignmentButtonBox(Pos.CENTER);

        VBox mainVBox = new VBox(10);
        mainVBox.getChildren().addAll(titleBox, fieldBox, getButtonBox());

        setCenter(mainVBox);

        Platform.runLater(() -> mainVBox.requestFocus());

        prepareStage(stage, generateStructure(300, 170, false));

        stage.setOnCloseRequest(e -> { // If user closes window, isClosing == true.
            isClosing = true;
        });

        stage.setResizable(false);
    }

    private void onOkButtonClick() {

        UserHandler userHandler = new UserHandler();
        if (checkUserInput(userHandler.checkExists(usernameField.getText()))) {
            try {
                userHandler.validateReturningUser(usernameField.getText(), passwordField.getText().getBytes());
                clearFields();
                new MainAccountWindow(getStage());
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
