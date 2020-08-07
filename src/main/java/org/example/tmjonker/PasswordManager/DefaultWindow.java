package org.example.tmjonker.PasswordManager;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.controlsfx.control.StatusBar;

/**
 * Subclasses inherit borderPane from DefaultWindow.  borderPane.top and borderPane.bottom are already
 * defined in the superclass with the menuBar on top and the statusBar on the bottom..
 * In the subclasses, you only have to implement borderPane.center/.right/.left.
 *
 * Subclasses must define a Stage.  Subclasses must request Scene from DefaultWindow using
 * generateStructure(width, height).
 *
 * All other components of GUI must be defined and created by subclasses.
 */

abstract class DefaultWindow {

    MenuBar menuBar = new MenuBar();
    Menu fileMenu = new Menu("_File");
    MenuItem newAccountItem = new MenuItem("_New Account");
    SeparatorMenuItem separatorItem = new SeparatorMenuItem();
    MenuItem closeMenuItem = new MenuItem("_Close Window");
    MenuItem exitMenuItem = new MenuItem("E_xit Program");
    StatusBar statusBar = new StatusBar();
    BorderPane borderPane = new BorderPane();

    public Scene generateStructure(int width, int height) {
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

    protected void onNewAccount() {
        new NewAccountWindow();
    }

    protected void disableCloseMenuItem() {
        closeMenuItem.setDisable(true);
    }

    protected void onExit() {
        System.exit(0);
    }

    abstract void onClose();

    protected void prepareStage(Stage stage, Scene scene) {
        stage.setScene(scene);
        stage.setTitle("Password Manager");
        stage.show();
    }
}
