package com.tmjonker.PasswordManager.GUI;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.GeneralSecurityException;


public class NewAccountWindow extends DefaultWindow {

    TextField usernameField;
    TextField password1Field;
    TextField password2Field;

    Button okButton = implementOkButton();
    Button cancelButton = implementCancelButton();

    boolean isClosing;

    public NewAccountWindow() {

        disableNewMenuItem(true);
        disableLogOutMenuItem(true);

        Text title = new Text("Create a new account");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setFocusTraversable(true);
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty());
        });

        password1Field = new PasswordField();
        password1Field.setPromptText("Password");
        password1Field.setFocusTraversable(true);

        password2Field = new PasswordField();
        password2Field.setPromptText("Confirm Password");
        password2Field.setFocusTraversable(true);

        okButton.setDisable(true);
        okButton.setOnAction(e -> onOkButtonClick());
        okButton.setOnMouseEntered(e -> setStatusBarText("Create user " + usernameField.getText()));
        okButton.setOnMouseExited(e -> setStatusBarText(""));

        cancelButton.setOnAction(e -> onClose());
        cancelButton.setOnMouseEntered(e -> setStatusBarText("Cancel"));
        cancelButton.setOnMouseExited(e -> setStatusBarText(""));

        addToButtonBox(okButton);
        addToButtonBox(cancelButton);
        setAlignmentButtonBox(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(title, 0,0,2, 1);
        gridPane.add(usernameField, 0, 1);
        gridPane.add(password1Field, 0, 2);
        gridPane.add(password2Field, 0, 3);
        gridPane.add(getButtonBox(), 0,4);

        setCenter(gridPane);

        Platform.runLater(() -> {

            getStage().setOnCloseRequest(e -> isClosing = true);

            Platform.runLater(() -> gridPane.requestFocus());

            Thread newThread = new Thread(new FormValidator());
            newThread.start();
        });

        prepareStage(new Stage(), generateStructure(250, 250, false));
        getStage().setResizable(false);
        getStage().setOnCloseRequest(e -> {
            isClosing = true;
        });
    }

    /*
    onOkButtonClick:
    userHandler.checkExists to check if username has already been used.  If it has been used, an error message pops up.
     */
    protected void onOkButtonClick() {

        if (checkUserInput(getUserHandler().checkExists(usernameField.getText()))) {

            try {
                getUserHandler().storeNewUser(usernameField.getText().getBytes(),
                        password1Field.getText().getBytes());
            } catch (IOException | GeneralSecurityException ex) {
                ex.printStackTrace();
            }
            new SuccessDialog("User " + usernameField.getText() + " has been created.", "Success");
            onClose();
        }

    }

    private boolean checkUserInput(boolean exists) {

        if (!password1Field.getText().equals(password2Field.getText())) {
            new ErrorDialog("Passwords do not match.", "ERROR");
            clearPasswordFields();
            return false;
        } else if (usernameField.getText().contains(" ")
                || password1Field.getText().contains(" ") || password2Field.getText().contains(" ")) {
            new ErrorDialog("No spaces are allowed.", "Error");
            clearAllFields();
            return false;
        } else if (password1Field.getText().trim().isEmpty() || password2Field.getText().trim().isEmpty()) {
            new ErrorDialog("Both password fields must be filled in.", "Error");
            clearPasswordFields();
            return false;
        } else if (exists) {
            new ErrorDialog("Username already exists!", "Error");
            clearAllFields();
            return false;
        } else return true;
    }

    private void clearAllFields() {

        usernameField.clear();
        password1Field.clear();
        password2Field.clear();
    }

    private void clearPasswordFields() {

        password1Field.clear();
        password2Field.clear();
    }

    public class FormValidator implements Runnable {

        @Override
        public void run() {
            while (!isClosing)
                checkFilled();
        }

        private void checkFilled() {

            okButton.setDisable(usernameField.getText().trim().isEmpty() || password1Field.getText().trim().isEmpty()
                    || password2Field.getText().trim().isEmpty());
        }
    }
}
