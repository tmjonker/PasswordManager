package org.example.tmjonker.PasswordManager;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainAccountWindow extends DefaultWindow {

    public MainAccountWindow() {

        TableView<Credential> table = new TableView<>();

        TableColumn<Credential, Type> typeColumn = new TableColumn<>("Type");
        typeColumn.setMinWidth(100);
        typeColumn.setCellValueFactory(new PropertyValueFactory<Credential, Type>("type"));

        TableColumn<Credential, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setMinWidth(100);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<Credential, String>("username"));

        TableColumn<Credential, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setMinWidth(100);
        passwordColumn.setCellValueFactory(new PropertyValueFactory<Credential, String>("password"));
    }

    protected void onClose() {

    }
}
