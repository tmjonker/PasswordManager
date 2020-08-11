package com.tmjonker.PasswordManager.GUI;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import org.controlsfx.control.StatusBar;

public class MainSideBar extends SideBar {

    TreeItem<String> websites, applications, email, financial, games;

    TreeItem<String> root = new TreeItem<>("All Passwords");

    public MainSideBar(StatusBar statusBar) {

        setStatusBar(statusBar);

        websites = generateTreeItem("Website Passwords", root);
        applications = generateTreeItem("Application Passwords", root);
        email = generateTreeItem("Email Passwords", root);
        financial = generateTreeItem("Financial Passwords", root);
        games = generateTreeItem("Game Passwords", root);

        root.setExpanded(true);

        TreeView<String> treeView = new TreeView<>(root);

        addToMainBox(treeView);

        treeView.prefHeightProperty().bind(getMainBox().heightProperty()); // Sets treeView to fill mainBox.
    }

    private void setStatusBarText(String text) {

        getStatusBar().setText(text);
    }

    private TreeItem<String> generateTreeItem(String text, TreeItem<String> parent) {

        TreeItem<String> treeItem = new TreeItem<>(text);
        treeItem.setExpanded(true);
        parent.getChildren().add(treeItem);
        return treeItem;
    }
}
