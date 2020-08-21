package com.tmjonker.passwordmanager.gui.dialog;

import com.tmjonker.passwordmanager.credentials.Credential;
import com.tmjonker.passwordmanager.credentials.CredentialHandler;
import com.tmjonker.passwordmanager.credentials.Type;
import com.tmjonker.passwordmanager.credentials.WebsiteCredential;
import com.tmjonker.passwordmanager.gui.window.MainWindow;
import com.tmjonker.passwordmanager.users.User;
import com.tmjonker.passwordmanager.users.UserHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Optional;

public class EditCredentialDialog {

    private final MainWindow mainWindow;

    private UserHandler userHandler;
    private CredentialHandler credentialHandler;

    private final User verifiedUser;

    private GridPane gridPane;
    private Dialog<Credential> inputDialog;

    private PasswordField passwordField;
    private TextField passwordTextField;

    private Button toggleButton;

    private boolean hide = true;

    public EditCredentialDialog(MainWindow mainWindow) {

        verifiedUser = mainWindow.getVerifiedUser();
        this.mainWindow = mainWindow;
        Credential selectedCredential = mainWindow.getInnerContainer().getSelectedRow();

        try {
            userHandler = new UserHandler();
            credentialHandler = new CredentialHandler();
        } catch (IOException | GeneralSecurityException ex) {
            new ExceptionDialog(ex);
        }

        showEditDialog(selectedCredential);
    }

    private void showEditDialog(Credential selectedCredential) {

        Type type = selectedCredential.getType();

        inputDialog = new Dialog<>();

        Stage stage = (Stage) inputDialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("password_16px.png"));

        inputDialog.setTitle("Edit existing " + type + " credential");
        inputDialog.setHeaderText("Edit existing " + type + " credential");

        inputDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        switch (type) {
            case WEBSITE:
                WebsiteCredential websiteCredential = (WebsiteCredential) selectedCredential;
                editWebsite(websiteCredential);
                break;
            case APPLICATION:
                break;
            case GAME:
                break;
            case EMAIL:
                break;
        }

    }

    private void editWebsite(WebsiteCredential websiteCredential) {

        inputDialog.setGraphic(new ImageView(new Image("website_48px.png")));

        toggleButton = new Button("Show");

        TextField urlField = new TextField(websiteCredential.getUrl().toString());
        urlField.setPromptText("URL");
        TextField usernameField = new TextField(websiteCredential.getUsername());
        usernameField.setPromptText("Username");
        passwordField = new PasswordField();
        passwordField.setText(websiteCredential.getDecryptedPassword());
        passwordField.setPromptText("Password");

        gridPane.add(new Label("URL:"), 0, 0);
        gridPane.add(urlField, 1, 0);
        gridPane.add(new Label("Username:"), 0, 1);
        gridPane.add(usernameField, 1, 1);
        gridPane.add(new Label("Password:"), 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(toggleButton, 2, 2);


        toggleButton.setOnAction(e -> {
                generateComponents();
        });

        inputDialog.getDialogPane().setContent(gridPane);

        Button okButton = (Button) inputDialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.addEventFilter(ActionEvent.ACTION, ae -> {

            String password;
            if (passwordField != null)
                password = passwordField.getText().trim();
            else
                password = passwordTextField.getText().trim();

            if (urlField.getText().trim().isEmpty()) {
                new ErrorDialog("URL field can't be empty.", "Error");
                ae.consume();
            } else if (usernameField.getText().trim().isEmpty()) {
                new ErrorDialog("Username field can't empty.", "Error");
                ae.consume();
            } else if (password.isEmpty()) {
                new ErrorDialog("Password field can't be empty.", "Error");
                ae.consume();
            }
        });

        inputDialog.setResultConverter(inputButton -> {
            if (inputButton == ButtonType.OK) {
                String password;
                if (passwordField != null)
                    password = passwordField.getText();
                else
                    password = passwordTextField.getText();

                websiteCredential.setUsername(usernameField.getText().trim());
                websiteCredential.setPassword(password.getBytes());

                try {
                    credentialHandler.encryptCredential(websiteCredential);
                } catch (GeneralSecurityException | IOException ex) {
                    new ExceptionDialog(ex);
                }

                try {
                    websiteCredential.setUrl(urlField.getText());
                } catch (URISyntaxException ex) {
                    new ExceptionDialog(ex);
                }

                return websiteCredential;
            }
            return null;
        });

        Optional<Credential> result = inputDialog.showAndWait();

        result.ifPresent(wc -> {
            try {
                userHandler.storeCredential(verifiedUser, wc);
            } catch (IOException ex) {
                new ExceptionDialog(ex);
            }
        });
    }

    private void generateComponents() {
        if (!hide) {
            passwordField = new PasswordField();
            passwordField.setText(passwordTextField.getText());
            passwordField.setPromptText("Password");
            gridPane.add(passwordField, 1, 2);
            passwordTextField = null;
            toggleButton.setText("Show");
            hide = true;
        } else {
            passwordTextField = new TextField(passwordField.getText());
            passwordTextField.setPromptText("Password");
            gridPane.add(passwordTextField, 1, 2);
            passwordField = null;
            toggleButton.setText("Hide");
            hide = false;
        }
    }
}
