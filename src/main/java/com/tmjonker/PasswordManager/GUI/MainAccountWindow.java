package com.tmjonker.PasswordManager.GUI;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.tmjonker.PasswordManager.Credentials.Credential;
import com.tmjonker.PasswordManager.Credentials.Type;
import javafx.stage.Stage;

import java.net.URI;

public class MainAccountWindow extends DefaultWindow {

    private final SideBar sideBar = new MainSideBar(getStatusBar());

    public MainAccountWindow(Stage stage) {

        disableNewMenuItem(true);
        disableLogOutMenuItem(false);
        disableCloseMenuItem(true);

        setLeft(sideBar);

        TableView<Credential> table = new TableView<>();

        TableColumn<Credential, Type> typeColumn = new TableColumn<>("Type");
        typeColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.20));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Credential, Type>("type"));

        TableColumn<Credential, URI> uriColumn = new TableColumn<>("URL");
        uriColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.40));
        uriColumn.setCellValueFactory(new PropertyValueFactory<Credential, URI>("uri"));

        TableColumn<Credential, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.20));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<Credential, String>("username"));

        TableColumn<Credential, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.20));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<Credential, String>("password"));

        table.getColumns().addAll(typeColumn, uriColumn, usernameColumn, passwordColumn);
        table.setFocusTraversable(false);

        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        setCenter(scrollPane);

        prepareStage(stage, generateStructure(800,600, true));
        stage.setResizable(true);
    }
}
