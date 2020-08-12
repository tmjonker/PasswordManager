package com.tmjonker.PasswordManager.GUI;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.StatusBar;

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
    private final Menu editMenu = new Menu("_Edit");
    private final Menu accountMenu = new Menu("_Account");
    private final Menu helpMenu = new Menu("_Help");

    private final MenuItem newAccountItem = new MenuItem("_New Account");
    private final MenuItem logOutMenuItem = new MenuItem("_Log Out");
    private final MenuItem closeMenuItem = new MenuItem("_Close Window");
    private final MenuItem exitMenuItem = new MenuItem("E_xit Program");

    private final ToolBar toolBar = new ToolBar();
    private final StatusBar statusBar = new StatusBar();

    private final VBox topVbox = new VBox();
    private final HBox buttonBox = new HBox(10);
    private final BorderPane borderPane = new BorderPane();

    private Stage stage;

    protected void setAlignmentButtonBox(Pos position) {

        buttonBox.setAlignment(position);
    }

    protected void setPaddingButtonBox(int top, int left, int bottom, int right) {

        buttonBox.setPadding(new Insets(top, left, bottom, right));
    }

    protected HBox getButtonBox() {

        return buttonBox;
    }

    protected Button implementOkButton() {

        Button okButton;
        okButton = new Button("Ok");
        okButton.setMinWidth(60);
        return okButton;
    }

    protected Button implementCancelButton() {

        Button cancelButton;
        cancelButton = new Button("Cancel");
        cancelButton.setMinWidth(60);
        return cancelButton;
    }

    protected void addToButtonBox(Button button) {

        buttonBox.getChildren().add(button);
    }

    protected Scene generateStructure(int width, int height, boolean hasToolBar) {

        menuBar.getMenus().addAll(fileMenu, editMenu, accountMenu, helpMenu);
        fileMenu.getItems().addAll(closeMenuItem, exitMenuItem);
        accountMenu.getItems().addAll(newAccountItem, logOutMenuItem);

        exitMenuItem.setOnAction(e -> onExit());
        newAccountItem.setOnAction(e -> onNewAccount());
        closeMenuItem.setOnAction(e -> onClose());

        topVbox.getChildren().add(menuBar);
        if (hasToolBar)
            implementToolBar();

        statusBar.setText("");
        borderPane.setTop(topVbox);
        borderPane.setBottom(statusBar);

        return new Scene(borderPane, width, height);
    }

    private void implementToolBar() {

        Button addButton = ButtonCreator.generateButton(new Image("add_24px.png"));
        addButton.setOnAction(e -> ToolBarHandler.onAddButtonClick());
        addButton.setOnMouseEntered(e -> setStatusBarText("Add a new credential"));
        addButton.setOnMouseExited(e -> setStatusBarText(""));

        Button editButton = ButtonCreator.generateButton(new Image("edit_file_24px.png"));
        editButton.setOnAction(e -> ToolBarHandler.onEditButtonClick());
        editButton.setOnMouseEntered(e -> setStatusBarText("Edit the selected credential"));
        editButton.setOnMouseExited(e -> setStatusBarText(""));

        Button removeButton = ButtonCreator.generateButton(new Image("delete_24px.png"));
        removeButton.setOnAction(e -> ToolBarHandler.onRemoveButtonClick());
        removeButton.setOnMouseEntered(e -> setStatusBarText("Remove the selected credential"));
        removeButton.setOnMouseExited(e -> setStatusBarText(""));

        toolBar.getItems().addAll(addButton, editButton, removeButton);

        topVbox.getChildren().add(toolBar);
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
        stage.getIcons().add(new Image("password_16px.png"));
        centerStage();
        stage.show();
    }

    protected void onNewAccount() {

        new NewAccountWindow();
    }

    protected void onClose() {

        stage.close();
    }

    protected void disableCloseMenuItem(boolean disabled) {
        closeMenuItem.setDisable(disabled);
    }

    protected void disableLogOutMenuItem(boolean disabled) {

        logOutMenuItem.setDisable(disabled);
    }

    protected void disableNewMenuItem(boolean disabled) {

        newAccountItem.setDisable(disabled); // disables the "New Account" option in the File menu.
    }

    protected void onExit() {
        System.exit(0);
    }
}
