package com.tmjonker.passwordmanager.gui.sidebar;

import com.tmjonker.passwordmanager.credentials.Credential;
import com.tmjonker.passwordmanager.credentials.Type;
import com.tmjonker.passwordmanager.gui.window.MainWindow;
import com.tmjonker.passwordmanager.users.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.List;

public class MainSideBar extends SideBar {

    private TreeItem<String> websites, applications, email, financial, games;
    private TreeItem<String> root = new TreeItem<>("All Passwords");

    private User verifiedUser;

    private MainWindow mainWindow;

    public MainSideBar(MainWindow window) {

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

        TreeView<String> treeView = new TreeView<>(root);

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(websites)) {
                mainWindow.getInnerContainer().setTableContent(generateObservableList(Type.WEBSITE));
            }
        });

        addToMainBox(treeView);

        //treeView.prefHeightProperty().bind(getMainBox().heightProperty()); // Sets treeView to fill mainBox.
    }

    private TreeItem<String> generateTreeItem(String text, TreeItem<String> parent) {

        TreeItem<String> treeItem = new TreeItem<>(text);
        treeItem.setExpanded(true);
        parent.getChildren().add(treeItem);
        return treeItem;
    }

    private ObservableList<Credential> generateObservableList(Type type) {

        List<Credential> list = verifiedUser.getCredentialCollection().get(type);
        return FXCollections.observableArrayList(list);
    }

    public void setVerifiedUser(User user) {

        verifiedUser = user;
    }
}
