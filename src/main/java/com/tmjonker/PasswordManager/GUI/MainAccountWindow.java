package com.tmjonker.PasswordManager.GUI;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.tmjonker.PasswordManager.Credentials.Credential;
import com.tmjonker.PasswordManager.Credentials.Type;
import javafx.stage.Stage;

import java.net.URI;

public class MainAccountWindow extends DefaultWindow {

    SideBar sideBar = new MainSideBar(getStatusBar());

    public MainAccountWindow(Stage stage) {

        disableNewMenuItem();

        setLeft(sideBar);

        TableView<Credential> table = new TableView<>();

        TableColumn<Credential, Type> typeColumn = new TableColumn<>("Type");
        typeColumn.setMinWidth(100);
        typeColumn.setCellValueFactory(new PropertyValueFactory<Credential, Type>("type"));

        TableColumn<Credential, URI> uriColumn = new TableColumn<>("URL");
        uriColumn.setMinWidth(280);
        uriColumn.setCellValueFactory(new PropertyValueFactory<Credential, URI>("uri"));

        TableColumn<Credential, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setMinWidth(150);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<Credential, String>("username"));

        TableColumn<Credential, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setMinWidth(150);
        passwordColumn.setCellValueFactory(new PropertyValueFactory<Credential, String>("password"));

        table.getColumns().addAll(typeColumn, uriColumn, usernameColumn, passwordColumn);

        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        setCenter(scrollPane);

        prepareStage(stage, generateStructure(800,600));
    }
}
