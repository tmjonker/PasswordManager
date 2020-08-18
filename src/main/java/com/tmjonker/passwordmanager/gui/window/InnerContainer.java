package com.tmjonker.passwordmanager.gui.window;

import com.tmjonker.passwordmanager.gui.toolbar.ToolBarHandler;
import com.tmjonker.passwordmanager.users.User;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import com.tmjonker.passwordmanager.credentials.Credential;
import com.tmjonker.passwordmanager.credentials.Type;

import java.net.URI;
import java.nio.charset.StandardCharsets;

public class InnerContainer {

    ScrollPane scrollPane;

    protected TableView<Credential> table;

    public InnerContainer() {

        generateInnerContainer();
    }

    private void generateInnerContainer() {

        table = new TableView<>();

        TableColumn<Credential, Type> typeColumn = new TableColumn<>("Type");
        typeColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.20));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Credential, Type>("type"));

        TableColumn<Credential, URI> uriColumn = new TableColumn<>("URL");
        uriColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.40));
        uriColumn.setCellValueFactory(new PropertyValueFactory<Credential, URI>("url"));

        TableColumn<Credential, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.20));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<Credential, String>("username"));

        TableColumn<Credential, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.20));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<Credential, String>("decryptedPassword"));

        table.getColumns().addAll(typeColumn, uriColumn, usernameColumn, passwordColumn);
        table.setFocusTraversable(false);

        scrollPane = new ScrollPane(table);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
    }

    public TableView<Credential> getTable() {

        return table;
    }

    public void setTableContent(ObservableList<Credential> list) {

        table.setItems(list);
    }

    public InnerContainer getMainAccountWindow() {

        return this;
    }

    public ScrollPane getScrollPane() {

        return scrollPane;
    }
}
