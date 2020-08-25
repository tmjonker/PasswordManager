package com.tmjonker.passwordmanager.gui.sidebar;

import com.tmjonker.passwordmanager.credentials.CredentialHandler;
import com.tmjonker.passwordmanager.credentials.Type;
import com.tmjonker.passwordmanager.gui.dialog.ExceptionDialog;
import com.tmjonker.passwordmanager.gui.window.MainWindow;
import com.tmjonker.passwordmanager.users.User;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class TreeBar {

    private CredentialHandler credentialHandler;
    private TreeItem<String> websites, applications, games;
    private final TreeItem<String> root = new TreeItem<>("All Passwords");
    private TreeView<String> treeView;
    private User verifiedUser;
    private final MainWindow mainWindow;
    private Type selected;

    public TreeBar(MainWindow window) {

        try {
            credentialHandler= new CredentialHandler();
        } catch (GeneralSecurityException ex) {
            new ExceptionDialog(ex);
        }
        mainWindow = window;
    }

    public void display() {

        verifiedUser = mainWindow.getVerifiedUser();

        websites = generateTreeItem("Website Passwords", root);
        applications = generateTreeItem("Application Passwords", root);
        games = generateTreeItem("Game Passwords", root);

        root.setExpanded(true);

        treeView = new TreeView<>(root);

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.equals(websites)) {
                    selected = Type.WEBSITE;
                    mainWindow.getInnerContainer()
                            .setTableContent(credentialHandler.generateObservableList(Type.WEBSITE, verifiedUser.getCredentialCollection()));
                } else if (newValue.equals(applications)) {
                    selected = Type.APPLICATION;
                    mainWindow.getInnerContainer()
                            .setTableContent(credentialHandler.generateObservableList(Type.APPLICATION, verifiedUser.getCredentialCollection()));
                } else if (newValue.equals(games)) {
                    selected = Type.GAME;
                    mainWindow.getInnerContainer()
                            .setTableContent(credentialHandler.generateObservableList(Type.GAME, verifiedUser.getCredentialCollection()));
                } else if (newValue.equals(root)) {
                    selected = null;
                    mainWindow.getInnerContainer()
                            .setTableContent(credentialHandler.generateObservableList(null, verifiedUser.getCredentialCollection()));
                } else {
                    mainWindow.getInnerContainer().setTableContent(null);
                }
            } catch (IOException | GeneralSecurityException ex) {
                new ExceptionDialog(ex);
            }
        });
        treeView.setPrefHeight(mainWindow.getInnerContainer().getScrollPane().getHeight() * 0.50); // sets height of sidebar to be 1/2 the height of the innercontainer.
    }

    private TreeItem<String> generateTreeItem(String text, TreeItem<String> parent) {

        TreeItem<String> treeItem = new TreeItem<>(text);
        treeItem.setExpanded(true);
        parent.getChildren().add(treeItem);
        return treeItem;
    }

    public TreeView<String> getTreeView() {

        return treeView;
    }

    public Type getSelected() {

        return selected;
    }
}
