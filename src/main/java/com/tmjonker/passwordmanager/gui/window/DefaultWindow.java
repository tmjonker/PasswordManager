package com.tmjonker.passwordmanager.gui.window;

import com.tmjonker.passwordmanager.gui.dialog.LoginDialog;
import com.tmjonker.passwordmanager.gui.dialog.NewUserDialog;
import com.tmjonker.passwordmanager.gui.dialog.SuccessDialog;
import com.tmjonker.passwordmanager.gui.sidebar.SideBar;
import com.tmjonker.passwordmanager.gui.toolbar.ButtonCreator;
import com.tmjonker.passwordmanager.gui.toolbar.ToolBarHandler;
import com.tmjonker.passwordmanager.users.User;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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

    protected final String DEFAULT_TITLE = "Password Manager - ";

    protected final MenuBar menuBar = new MenuBar();

    protected final Menu fileMenu = new Menu("_File");
    protected final Menu editMenu = new Menu("_Edit");
    protected final Menu accountMenu = new Menu("_Account");
    protected final Menu helpMenu = new Menu("_Help");

    protected final MenuItem newAccountItem = new MenuItem("_New Account");
    protected final MenuItem logInMenuItem = new MenuItem("Log _In");
    protected final MenuItem logOutMenuItem = new MenuItem("_Log Out");
    protected final MenuItem exitMenuItem = new MenuItem("E_xit Program");

    protected Button addButton;
    protected Button editButton;
    protected Button removeButton;

    protected final ToolBar toolBar = new ToolBar();
    protected final StatusBar statusBar = new StatusBar();

    protected final VBox topVbox = new VBox();
    protected final BorderPane borderPane = new BorderPane();

    protected Stage stage;

    protected User verifiedUser;

    protected ToolBarHandler toolBarHandler;

    public DefaultWindow() {

        stage = new Stage();
    }

    protected Scene generateStructure(int width, int height, boolean hasToolBar) {

        menuBar.getMenus().addAll(fileMenu, editMenu, accountMenu, helpMenu);
        fileMenu.getItems().addAll(exitMenuItem);
        accountMenu.getItems().addAll(logInMenuItem,newAccountItem,logOutMenuItem);

        exitMenuItem.setOnAction(e -> onExit());
        logInMenuItem.setOnAction(e -> onLogIn());
        newAccountItem.setOnAction(e -> onNewAccount());
        logOutMenuItem.setOnAction(e -> onLogOut());

        topVbox.getChildren().add(menuBar);

        if (hasToolBar)
            implementToolBar();

        statusBar.setText("");
        borderPane.setTop(topVbox);
        borderPane.setBottom(statusBar);

        return new Scene(borderPane, width, height);
    }

    private void implementToolBar() {

        toolBarHandler = new ToolBarHandler(null);

        addButton = ButtonCreator.generateButton(new Image("add_24px.png"));
        addButton.setOnAction(e -> toolBarHandler.onAddButtonClick());
        addButton.setOnMouseEntered(e -> setStatusBarText("Add a new credential"));
        addButton.setOnMouseExited(e -> setStatusBarText(""));

        editButton = ButtonCreator.generateButton(new Image("edit_file_24px.png"));
        editButton.setOnAction(e -> toolBarHandler.onEditButtonClick());
        editButton.setOnMouseEntered(e -> setStatusBarText("Edit the selected credential"));
        editButton.setOnMouseExited(e -> setStatusBarText(""));

        removeButton = ButtonCreator.generateButton(new Image("delete_24px.png"));
        removeButton.setOnAction(e -> toolBarHandler.onRemoveButtonClick());
        removeButton.setOnMouseEntered(e -> setStatusBarText("Remove the selected credential"));
        removeButton.setOnMouseExited(e -> setStatusBarText(""));

        setLoggedInConfig(false);

        toolBar.getItems().addAll(addButton, editButton, removeButton);

        topVbox.getChildren().add(toolBar);
    }

    protected void setCenter(Node node) {

        borderPane.setCenter(node);
    }

    protected void setLeft(SideBar sideBar) {

        borderPane.setLeft(sideBar.getMainBox());
    }

    protected void setStatusBarText(String text) {

        statusBar.setText(text);
    }

    protected void centerStage() {

        stage.centerOnScreen();
    }

    protected void prepareStage(Scene scene) {

        stage.setScene(scene);
        setStageTitle(DEFAULT_TITLE);
        stage.getIcons().add(new Image("password_16px.png"));
        centerStage();
        stage.show();
    }

    protected void setStageTitle(String text) {

        stage.setTitle(DEFAULT_TITLE + text);
    }

    protected void onLogIn() {

        LoginDialog loginDialog = new LoginDialog();

        if (loginDialog.getLoggedIn()) {
            verifiedUser = loginDialog.getVerifiedUser();
            setStageTitle(verifiedUser.getUsername());
            new SuccessDialog(verifiedUser.getUsername() + " has been logged in.", "Success");
            setLoggedInConfig(true);
        }
    }

    protected void setLoggedInConfig(boolean result) {

        logInMenuItem.setDisable(result);
        newAccountItem.setDisable(result);
        logOutMenuItem.setDisable(!result);

        addButton.setDisable(!result);
        editButton.setDisable(!result);
        removeButton.setDisable(!result);
    }

    protected void onNewAccount() {

        NewUserDialog newUserDialog = new NewUserDialog();

        if (newUserDialog.newUserCreated()) {
            verifiedUser = newUserDialog.getVerifiedUser();
            setStageTitle(verifiedUser.getUsername());
            new SuccessDialog("An account for " + verifiedUser.getUsername() + " has been created.",
                    "Success");
            setLoggedInConfig(true);
        }
    }

    protected void onLogOut() {

        verifiedUser = null;
        setStageTitle("");
        setLoggedInConfig(false);
        new SuccessDialog("You have successfully logged out.", "Success");
    }

    protected void onExit() {
        System.exit(0);
    }
}
