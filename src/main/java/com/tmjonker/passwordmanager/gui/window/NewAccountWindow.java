package com.tmjonker.passwordmanager.gui.window;

import com.tmjonker.passwordmanager.gui.dialog.ErrorDialog;
import com.tmjonker.passwordmanager.gui.dialog.ExceptionDialog;
import com.tmjonker.passwordmanager.gui.dialog.SuccessDialog;
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


public class NewAccountWindow extends DefaultWindow implements WindowShell  {

    TextField usernameField;
    TextField password1Field;
    TextField password2Field;

    Button okButton = implementOkButton();
    Button cancelButton = implementCancelButton();

    Stage stage;

    boolean isClosing;

    public NewAccountWindow(Stage stage) {

        this.stage = stage;

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

        cancelButton.setOnAction(e -> onCancelButtonClick());
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

            Platform.runLater(() -> gridPane.requestFocus());

            Thread newThread = new Thread(new FormValidator());
            newThread.start();
        });

        prepareStage(stage, generateStructure(300, 250, false));
        getStage().setResizable(false);
        stage.setOnCloseRequest(e -> onStageCloseRequest());
    }

    /*
    onOkButtonClick:
    userHandler.checkExists to check if username has already been used.  If it has been used, an error message pops up.
     */
    protected void onOkButtonClick() {

        String username = usernameField.getText().toLowerCase();
        String password1 = password1Field.getText();
        String password2 = password2Field.getText();

        try {
            if (checkUserInput(username, password1, password2)) {
                try {
                    getUserHandler().storeUser(getUserHandler().createUser(username.getBytes(),
                            password1.getBytes()));
                    new SuccessDialog("User " + username + " has been created.", "Success");
                    stage.close(); // Closes stage.
                    new LoginWindow(new Stage()); // Creates new stage.
                } catch (GeneralSecurityException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (IOException ex) {
            new ExceptionDialog(ex);
        }
    }

    private void onCancelButtonClick() {

        new LoginWindow(stage);
    }

    private boolean checkUserInput(String username, String password1, String password2) throws IOException {

        if (!password1.equals(password2)) {
            new ErrorDialog("Passwords do not match.", "ERROR");
            clearPasswordFields();
            return false;
        } else if (username.contains(" ")
                || password1.contains(" ") || password2.contains(" ")) {
            new ErrorDialog("No spaces are allowed.", "Error");
            clearAllFields();
            return false;
        } else if (password1.trim().isEmpty() || password2.trim().isEmpty()) {
            new ErrorDialog("Both password fields must be filled in.", "Error");
            clearPasswordFields();
            return false;
        } else if (getUserHandler().checkUsernameExists(username)) {
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

            okButton.setDisable(usernameField.getText().trim().isEmpty() || password1Field.getText().trim().isEmpty()
                    || password2Field.getText().trim().isEmpty());
        }
    }
}
