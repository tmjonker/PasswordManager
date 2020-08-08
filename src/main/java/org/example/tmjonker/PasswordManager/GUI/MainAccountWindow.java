package org.example.tmjonker.PasswordManager.GUI;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.tmjonker.PasswordManager.Credential.Credential;
import org.example.tmjonker.PasswordManager.Credential.Type;

import java.net.URI;

public class MainAccountWindow extends DefaultWindow {

    public MainAccountWindow() {

        TableView<Credential> table = new TableView<>();

        TableColumn<Credential, Type> typeColumn = new TableColumn<>("Type");
        typeColumn.setMinWidth(100);
        typeColumn.setCellValueFactory(new PropertyValueFactory<Credential, Type>("type"));

        TableColumn<Credential, URI> uriColumn = new TableColumn<>("URL");
        uriColumn.setMinWidth(200);
        uriColumn.setCellValueFactory(new PropertyValueFactory<Credential, URI>("uri"));

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
