package com.tmjonker.PasswordManager.GUI;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.controlsfx.control.StatusBar;

public class SideBar {

    VBox mainBox = new VBox();
    private StatusBar statusBar;

    public SideBar() {

        VBox.setVgrow(mainBox, javafx.scene.layout.Priority.ALWAYS);
    }

    protected void setStatusBar(StatusBar statusBar) {

        this.statusBar = statusBar;
    }

    protected StatusBar getStatusBar() {

        return statusBar;
    }

    protected void addToMainBox(Node node) {

        mainBox.getChildren().add(node);
    }

    protected VBox getMainBox() {

        return mainBox;
    }

    protected void setMainBoxPadding(int top, int left, int bottom, int right) {

        mainBox.setPadding(new Insets(top, left, bottom, right));
    }
}
