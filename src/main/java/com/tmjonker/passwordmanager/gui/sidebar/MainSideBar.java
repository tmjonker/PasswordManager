package com.tmjonker.passwordmanager.gui.sidebar;

import com.tmjonker.passwordmanager.credentials.Credential;
import com.tmjonker.passwordmanager.credentials.CredentialHandler;
import com.tmjonker.passwordmanager.credentials.Type;
import com.tmjonker.passwordmanager.encryption.EncryptionHandler;
import com.tmjonker.passwordmanager.gui.dialog.ExceptionDialog;
import com.tmjonker.passwordmanager.gui.window.MainWindow;
import com.tmjonker.passwordmanager.users.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class MainSideBar extends SideBar {

    private CredentialHandler credentialHandler;

    private TreeItem<String> websites, applications, email, financial, games;
    private final TreeItem<String> root = new TreeItem<>("All Passwords");

    private TreeView<String> treeView;

    private User verifiedUser;

    private final MainWindow mainWindow;

    public MainSideBar(MainWindow window) {

        try {
            credentialHandler= new CredentialHandler();
        } catch (GeneralSecurityException ex) {
            new ExceptionDialog(ex);
        }
        mainWindow = window;
    }

    public void generateMainSideBar() {

        verifiedUser = mainWindow.getVerifiedUser();

        websites = generateTreeItem("Website Passwords", root);
        applications = generateTreeItem("Application Passwords", root);
        email = generateTreeItem("Email Passwords", root);
        financial = generateTreeItem("Financial Passwords", root);
        games = generateTreeItem("Game Passwords", root);

        root.setExpanded(true);

        treeView = new TreeView<>(root);

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(websites)) {
                mainWindow.getInnerContainer()
                        .setTableContent(credentialHandler.generateObservableList(Type.WEBSITE, verifiedUser));
            } else {
                mainWindow.getInnerContainer().setTableContent(null);
            }
        });
        addToMainBox(treeView);
    }

    private TreeItem<String> generateTreeItem(String text, TreeItem<String> parent) {

        TreeItem<String> treeItem = new TreeItem<>(text);
        treeItem.setExpanded(true);
        parent.getChildren().add(treeItem);
        return treeItem;
    }

    public void setVerifiedUser(User user) {

        verifiedUser = user;
    }

    public TreeView<String> getTreeView() {

        return treeView;
    }
}
