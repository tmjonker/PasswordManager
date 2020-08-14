package com.tmjonker.passwordmanager.gui.window;

import com.tmjonker.passwordmanager.gui.dialog.ErrorDialog;
import com.tmjonker.passwordmanager.gui.dialog.ExceptionDialog;
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
public class LoginWindow extends DefaultWindow implements WindowShell {

    TextField usernameField;
    PasswordField passwordField;

    Button okButton = implementOkButton();

    boolean isClosing;

    Stage stage;

    public LoginWindow(Stage stage) {

        this.stage = stage;

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

        Platform.runLater(() -> {

            mainVBox.requestFocus();

            Thread newThread = new Thread(new FormValidator());
            newThread.start();
        });

        prepareStage(stage, generateStructure(300, 170, false));
        stage.setResizable(false);
        stage.setOnCloseRequest(e -> onStageCloseRequest());
    }

    private void onOkButtonClick() {

        String username = usernameField.getText().toLowerCase();
        String password = passwordField.getText().toLowerCase();

        try {
            if (checkUserInput(username, password)) {
                if (getUserHandler().validateReturningUser(username, password.getBytes())) {
                    new MainAccountWindow(new Stage());
                    stage.close();
                } else {
                    new ErrorDialog("The password that you entered is incorrect.", "Error");
                }
            }
        } catch(GeneralSecurityException | IOException ex){
            new ExceptionDialog(ex);
        }
    }

    private boolean checkUserInput(String username, String password) throws IOException {

        if (username.contains(" ")
                || password.contains(" ")) {
            clearAllFields();
            new ErrorDialog("No spaces are allowed.", "Error");
            return false;
        } else if (!getUserHandler().checkUsernameExists(usernameField.getText())) {
            new ErrorDialog("User " + usernameField.getText() + " does not exist.", "Error");
            clearAllFields();
            return false;
        } else {
            return true;
        }
    }

    private void clearAllFields() {

        usernameField.clear();
        passwordField.clear();
    }

    @Override
    public void onStageCloseRequest() {

        isClosing = true;
        System.exit(0);
    }

    public class FormValidator implements Runnable {

        @Override
        public void run() {
            while (!isClosing)
                checkFilled();
        }

        private void checkFilled() {

            okButton.setDisable(usernameField.getText().trim().isEmpty()
                    || passwordField.getText().trim().isEmpty());
        }
    }
}
