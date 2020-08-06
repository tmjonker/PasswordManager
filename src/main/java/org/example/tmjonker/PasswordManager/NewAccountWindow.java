package org.example.tmjonker.PasswordManager;

import com.google.protobuf.Message;
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
import org.example.tmjonker.PasswordManager.MessageBox;

import java.io.IOException;
import java.security.GeneralSecurityException;


public class NewAccountWindow extends DefaultWindow {

    Stage stage = new Stage();

    TextField usernameField;
    TextField password1Field;
    TextField password2Field;

    Button okButton;
    Button cancelButton;

    public NewAccountWindow() {
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

        borderPane.setCenter(gridPane);

        prepareStage(stage, generateStructure(250, 250));
    }

    @Override
    protected void onClose() {
        stage.close();
    }

    protected void onOkButtonClick() {
        if (!password1Field.getText().equals(password2Field.getText()))
            new MessageBox("Passwords do not match.", "ERROR");
        else {
            try {
                new UserHandler(TinkPasswordVault.encryptCredentials(usernameField.getText().getBytes(),
                        password1Field.getText().getBytes()));
                new MessageBox("User " + usernameField.getText() + "has been created.", "Success");
                stage.close();
            } catch (GeneralSecurityException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
