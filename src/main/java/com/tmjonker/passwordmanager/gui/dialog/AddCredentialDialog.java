package com.tmjonker.passwordmanager.gui.dialog;

import com.tmjonker.passwordmanager.credentials.Credential;
import com.tmjonker.passwordmanager.credentials.CredentialHandler;
import com.tmjonker.passwordmanager.credentials.Type;
import com.tmjonker.passwordmanager.credentials.WebsiteCredential;
import com.tmjonker.passwordmanager.encryption.EncryptionHandler;
import com.tmjonker.passwordmanager.gui.window.MainWindow;
import com.tmjonker.passwordmanager.properties.PropertiesHandler;
import com.tmjonker.passwordmanager.users.User;
import com.tmjonker.passwordmanager.users.UserHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddCredentialDialog {

    private final MainWindow mainWindow;

    private UserHandler userHandler;
    private CredentialHandler credentialHandler;

    private final User verifiedUser;

    private GridPane gridPane;
    private Dialog<Credential> inputDialog;

    private ButtonType addButtonType;

    public AddCredentialDialog(MainWindow mainWindow) {

        verifiedUser = mainWindow.getVerifiedUser();
        this.mainWindow = mainWindow;

        try {
            userHandler = new UserHandler();
            credentialHandler = new CredentialHandler();
        } catch (IOException | GeneralSecurityException ex) {
            new ExceptionDialog(ex);
        }

        showChoiceDialog();
    }

    private void showChoiceDialog() {

        List<Type> typeList = new ArrayList<>();
        typeList.add(Type.WEBSITE);

        ChoiceDialog<Type> choiceDialog = new ChoiceDialog<>(Type.WEBSITE, typeList);

        Stage stage = (Stage) choiceDialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("password_16px.png"));

        choiceDialog.setTitle("Select a type");
        choiceDialog.setHeaderText("What kind of password do you want to add?");
        choiceDialog.setGraphic(new ImageView(new Image("question_mark_48px.png")));

        Optional<Type> result = choiceDialog.showAndWait();

        result.ifPresent(type -> showAddDialog(type));
    }

    private void showAddDialog(Type type) {

        inputDialog = new Dialog<>();

        Stage stage = (Stage) inputDialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("password_16px.png"));

        inputDialog.setTitle("Add a new " + type);
        inputDialog.setHeaderText("Add a new " + type);

        addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        inputDialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        switch (type) {
            case WEBSITE:
                        addWebsite();
                        break;
            case APPLICATION:
                        break;
            case GAME:
                        break;
            case EMAIL:
                        break;
        }

    }

    private void addWebsite() {

        inputDialog.setGraphic(new ImageView(new Image("website_48px.png")));

        TextField urlField = new TextField();
        urlField.setPromptText("URL");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        gridPane.add(new Label("URL:"), 0, 0);
        gridPane.add(urlField, 1, 0);
        gridPane.add(new Label("Username:"), 0, 1);
        gridPane.add(usernameField, 1, 1);
        gridPane.add(new Label("Password:"), 0, 2);
        gridPane.add(passwordField, 1, 2);

        inputDialog.getDialogPane().setContent(gridPane);

        Button addButton = (Button) inputDialog.getDialogPane().lookupButton(addButtonType);
        addButton.addEventFilter(ActionEvent.ACTION, ae -> {

            if (urlField.getText().trim().isEmpty()) {
                new ErrorDialog("URL field can't be empty.", "Error");
                ae.consume();
            } else if (usernameField.getText().trim().isEmpty()) {
                new ErrorDialog("Username field can't empty.", "Error");
                ae.consume();
            } else if (passwordField.getText().trim().isEmpty()) {
                new ErrorDialog("Password field can't be empty.", "Error");
                ae.consume();
            }
        });

        inputDialog.setResultConverter(inputButton -> {
            if (inputButton == addButtonType) {
                return new WebsiteCredential(urlField.getText().trim(), usernameField.getText().trim(),
                        passwordField.getText());
            }
            return null;
        });

        Optional<Credential> result = inputDialog.showAndWait();

        result.ifPresent(wc -> {
            try {
                userHandler.storeCredential(verifiedUser, credentialHandler.finalizeCredential(wc));
            } catch (IOException | GeneralSecurityException ex) {
                new ExceptionDialog(ex);
            }
        });
    }
}
