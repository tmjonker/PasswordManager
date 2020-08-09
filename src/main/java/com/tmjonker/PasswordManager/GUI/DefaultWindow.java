package com.tmjonker.PasswordManager.GUI;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.controlsfx.control.StatusBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Subclasses inherit borderPane from DefaultWindow.  borderPane.top and borderPane.bottom are already
 * defined in the superclass with the menuBar on top and the statusBar on the bottom..
 * In the subclasses, you have to implement borderPane.center/.right/.left.
 *
 * Subclasses must define a Stage.  Subclasses must request Scene from DefaultWindow using
 * generateStructure(stage, width, height).
 *
 * All other components of GUI must be defined and created by subclasses.
 */

public class DefaultWindow {

    private final MenuBar menuBar = new MenuBar();
    private final Menu fileMenu = new Menu("_File");
    private final MenuItem newAccountItem = new MenuItem("_New Account");
    private final SeparatorMenuItem separatorItem = new SeparatorMenuItem();
    private final MenuItem closeMenuItem = new MenuItem("_Close Window");
    private final MenuItem exitMenuItem = new MenuItem("E_xit Program");
    private final StatusBar statusBar = new StatusBar();
    private final BorderPane borderPane = new BorderPane();
    private Stage stage;

    protected Scene generateStructure(int width, int height) {
        menuBar.getMenus().add(fileMenu);
        fileMenu.getItems().add(newAccountItem);
        fileMenu.getItems().add(separatorItem);
        fileMenu.getItems().add(closeMenuItem);
        fileMenu.getItems().add(exitMenuItem);

        exitMenuItem.setOnAction(e -> onExit());
        newAccountItem.setOnAction(e -> onNewAccount());
        closeMenuItem.setOnAction(e -> onClose());

        statusBar.setText("");
        borderPane.setTop(menuBar);
        borderPane.setBottom(statusBar);

        return new Scene(borderPane, width, height);
    }

    protected void setCenter(Node node) {

        borderPane.setCenter(node);
    }

    protected void setLeft(SideBar sideBar) {

        borderPane.setLeft(sideBar.getMainBox());
    }

    private void setStage(Stage stage) {

        this.stage = stage;
    }

    protected void setStatusBarText(String text) {

        getStatusBar().setText(text);
    }

    protected void centerStage() {

        stage.centerOnScreen();
    }

    protected StatusBar getStatusBar() {

        return statusBar;
    }

    protected Stage getStage() {

        return stage;
    }

    protected void prepareStage(Stage aStage, Scene scene) {
        setStage(aStage);
        stage.setScene(scene);
        stage.setTitle("Password Manager");
        centerStage();
        stage.show();
    }

    protected void onNewAccount() {
        new NewAccountWindow();
    }

    protected void onClose() {
        stage.close();
    }

    protected void disableCloseMenuItem() {
        closeMenuItem.setDisable(true);
    }

    protected void disableNewMenuItem() {

        newAccountItem.setDisable(true); // disables the "New Account" option in the File menu.
    }

    protected void onExit() {
        System.exit(0);
    }
}
