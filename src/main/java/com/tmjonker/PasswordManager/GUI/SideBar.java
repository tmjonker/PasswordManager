package com.tmjonker.PasswordManager.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.controlsfx.control.StatusBar;

public class SideBar {

    VBox mainBox = new VBox(10);
    private StatusBar statusBar;

    public SideBar() {

        mainBox.setMinWidth(90);
        mainBox.setAlignment(Pos.TOP_CENTER);
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

    protected void setMainBoxAlignment(Pos position) {

        mainBox.setAlignment(position);
    }
}
