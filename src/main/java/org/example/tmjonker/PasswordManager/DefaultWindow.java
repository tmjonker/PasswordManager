package org.example.tmjonker.PasswordManager;

import javafx.scene.Node;
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

        statusBar.setText("");
        borderPane.setTop(menuBar);
        borderPane.setBottom(statusBar);

        return new Scene(borderPane, width, height);
    }

    protected void setCenter(Node node) {

        borderPane.setCenter(node);
    }

    private void setStage(Stage stage) {

        this.stage = stage;
    }

    protected void prepareStage(Stage aStage, Scene scene) {
        setStage(aStage);
        stage.setScene(scene);
        stage.setTitle("Password Manager");
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
