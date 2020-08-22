package com.tmjonker.passwordmanager.gui.sidebar;

import com.tmjonker.passwordmanager.gui.window.MainWindow;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;

public class SideBar {

    private final VBox mainBox = new VBox();
    private final MainWindow mainWindow;
    private final TreeBar treeBar;

    public SideBar(MainWindow mainWindow) {

        this.mainWindow = mainWindow;
         treeBar = new TreeBar(mainWindow);
    }

    public void generateSideBar() {

        Text header = new Text("Password Manager");
        header.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        header.setTextAlignment(TextAlignment.CENTER);

        Image emblem = new Image("security_lock_112px.png");
        ImageView imageView = new ImageView(emblem);
        VBox imageBox = new VBox(10);
        imageBox.getChildren().add(imageView);
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setPadding(new Insets(55, 0, 0, 0));;

        treeBar.display(); // generates TreeView object and all associated TreeItems.

        mainBox.getChildren().addAll(treeBar.getTreeView(), imageBox);
        mainBox.setAlignment(Pos.TOP_CENTER);
    }

    public VBox getMainBox() {

        return mainBox;
    }
}
