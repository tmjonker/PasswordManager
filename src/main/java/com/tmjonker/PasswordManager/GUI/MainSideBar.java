package com.tmjonker.PasswordManager.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import org.controlsfx.control.StatusBar;

public class MainSideBar extends SideBar {

    public MainSideBar(StatusBar statusBar) {

        setStatusBar(statusBar);

        Button addButton = new Button("Add");
        addButton.setMinWidth(60);
        addButton.setAlignment(Pos.CENTER);
        addButton.setOnMouseEntered(e -> setStatusBarText("Add new credential"));
        addButton.setOnMouseExited(e -> setStatusBarText(""));

        Button removeButton = new Button("Remove");
        removeButton.setMinWidth(60);
        removeButton.setAlignment(Pos.CENTER);
        removeButton.setOnMouseEntered(e -> setStatusBarText("Remove existing credential"));
        removeButton.setOnMouseExited(e -> setStatusBarText(""));

        addToMainBox(addButton);
        addToMainBox(removeButton);

        setMainBoxPadding(40, 0, 0, 0);
    }

    private void setStatusBarText(String text) {

        getStatusBar().setText(text);
    }
}
