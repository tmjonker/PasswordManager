package org.example.tmjonker.PasswordManager.GUI;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.tmjonker.PasswordManager.User.UserHandler;

import java.io.IOException;
import java.security.GeneralSecurityException;


public class NewAccountWindow extends DefaultWindow {

    TextField usernameField;
    TextField password1Field;
    TextField password2Field;

    Button okButton;
    Button cancelButton;

    public NewAccountWindow() {

        disableNewMenuItem();

        Text title = new Text("Create a new account");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setFocusTraversable(true);

        password1Field = new PasswordField();
        password1Field.setPromptText("Password");
        password1Field.setFocusTraversable(true);

        password2Field = new PasswordField();
        password2Field.setPromptText("Confirm Password");
        password2Field.setFocusTraversable(true);

        okButton = new Button("Ok");
        Platform.runLater(() -> okButton.requestFocus());
        okButton.setOnAction(e -> onOkButtonClick());

        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> onClose());

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(okButton,cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(title, 0,0,2, 1);
        gridPane.add(usernameField, 0, 1);
        gridPane.add(password1Field, 0, 2);
        gridPane.add(password2Field, 0, 3);
        gridPane.add(buttonBox, 0,4);

        setCenter(gridPane);

        prepareStage(new Stage(), generateStructure(250, 250));
    }

    /*
    onOkButtonClick:
    userHandler.checkExists to check if username has already been used.  If it has been used, an error message pops up.
     */
    protected void onOkButtonClick() {

        UserHandler userHandler = new UserHandler();

        if (checkUserInput(userHandler.checkExists(usernameField.getText()))) {

            try {
                userHandler.storeNewUser(usernameField.getText().getBytes(),
                        password1Field.getText().getBytes());
            } catch (IOException | GeneralSecurityException ex) {
                ex.printStackTrace();
            }

            new MessageBox("User " + usernameField.getText() + " has been created.", "Success");
            onClose();
        }

    }

    private boolean checkUserInput(boolean exists) {

        if (!password1Field.getText().equals(password2Field.getText())) {
            clearFields();
            new MessageBox("Passwords do not match.", "ERROR");
            return false;
        } else if (usernameField.getText().contains(" ")
                || password1Field.getText().contains(" ") || password2Field.getText().contains(" ")) {
            clearFields();
            new MessageBox("No spaces are allowed.", "Error");
            return false;
        } else if (exists) {
            clearFields();
            new MessageBox("Username already exists!", "Error");
            return false;
        } else return true;
    }

    private void clearFields() {

        usernameField.clear();
        password1Field.clear();
        password2Field.clear();
    }
}
