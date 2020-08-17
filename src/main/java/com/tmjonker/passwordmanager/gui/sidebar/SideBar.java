package com.tmjonker.passwordmanager.gui.sidebar;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class SideBar {

    VBox mainBox = new VBox();

    protected void addToMainBox(Node node) {

        mainBox.getChildren().add(node);
    }

    public VBox getMainBox() {

        return mainBox;
    }
}
