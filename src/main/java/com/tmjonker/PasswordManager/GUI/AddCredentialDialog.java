package com.tmjonker.PasswordManager.GUI;

import com.tmjonker.PasswordManager.Credentials.Credential;
import com.tmjonker.PasswordManager.Credentials.CredentialHandler;
import com.tmjonker.PasswordManager.Credentials.Type;
import com.tmjonker.PasswordManager.Credentials.WebsiteCredential;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddCredentialDialog {

    public AddCredentialDialog() {
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

        CredentialHandler credentialHandler = new CredentialHandler();

        Dialog<Credential> inputDialog = new Dialog<>();

        Stage stage = (Stage) inputDialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("password_16px.png"));

        inputDialog.setTitle("Add a new " + type);
        inputDialog.setHeaderText("Add a new " + type);
        inputDialog.setGraphic(new ImageView(new Image("website_48px.png")));

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        inputDialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

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

        Node addButton = inputDialog.getDialogPane().lookupButton(addButtonType);
        addButton.setDisable(true);

        inputDialog.getDialogPane().setContent(gridPane);

        inputDialog.setResultConverter(inputButton -> {
            if (inputButton == addButtonType) {
                return new WebsiteCredential(urlField.getText(), usernameField.getText(), passwordField.getText());
            }
            return null;
        });

        Optional<Credential> result = inputDialog.showAndWait();

        result.ifPresent(wc -> credentialHandler.storeCredential(wc));
    }
}
