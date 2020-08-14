package com.tmjonker.passwordmanager.gui.sidebar;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.controlsfx.control.StatusBar;

public class SideBar {

    VBox mainBox = new VBox();
    private StatusBar statusBar;

    public SideBar() {

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

    public VBox getMainBox() {

        return mainBox;
    }
}
