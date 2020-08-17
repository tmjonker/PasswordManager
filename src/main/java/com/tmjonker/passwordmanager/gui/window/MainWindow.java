package com.tmjonker.passwordmanager.gui.window;

import com.tmjonker.passwordmanager.gui.dialog.LoginDialog;
import com.tmjonker.passwordmanager.gui.dialog.NewUserDialog;
import com.tmjonker.passwordmanager.gui.dialog.SuccessDialog;
import com.tmjonker.passwordmanager.gui.sidebar.MainSideBar;
import com.tmjonker.passwordmanager.gui.toolbar.ButtonCreator;
import com.tmjonker.passwordmanager.gui.toolbar.ToolBarHandler;
import com.tmjonker.passwordmanager.users.User;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.StatusBar;

public class MainWindow implements WindowShell{

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
    protected MainSideBar sideBar;

    protected final VBox topVbox = new VBox();
    protected final BorderPane borderPane = new BorderPane();

    protected Stage stage = new Stage();

    protected User verifiedUser;

    protected ToolBarHandler toolBarHandler = new ToolBarHandler(this);

    protected InnerContainer innerContainer;

    public MainWindow() {

        prepareStage(generateStructure(1000, 600, true));
    }

    private Scene generateStructure(int width, int height, boolean hasToolBar) {

        innerContainer = new InnerContainer();
        sideBar = new MainSideBar(this);
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
        borderPane.setLeft(sideBar.getMainBox());
        borderPane.setCenter(innerContainer.getScrollPane());

        return new Scene(borderPane, width, height);
    }

    private void implementToolBar() {

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
        stage.setResizable(true);
        stage.setOnCloseRequest(e -> onStageCloseRequest());
    }

    protected void setStageTitle(String text) {

        stage.setTitle(DEFAULT_TITLE + text);
    }

    protected void onLogIn() {

        LoginDialog loginDialog = new LoginDialog();

        if (loginDialog.getLoggedIn()) {
            updateVerifiedUser(loginDialog.getVerifiedUser());
            sideBar.generateMainSideBar();
            setLoggedInConfig(true);

            new SuccessDialog(verifiedUser.getUsername() + " has been logged in.", "Success");
        }
    }

    protected void setLoggedInConfig(boolean result) {

        logInMenuItem.setDisable(result);
        newAccountItem.setDisable(result);
        logOutMenuItem.setDisable(!result);

        addButton.setDisable(!result);
        editButton.setDisable(!result);
        removeButton.setDisable(!result);

        if (result)
            setStageTitle(verifiedUser.getUsername());
        else
            setStageTitle("");
    }

    protected void onNewAccount() {

        NewUserDialog newUserDialog = new NewUserDialog();

        if (newUserDialog.newUserCreated()) {
            updateVerifiedUser(newUserDialog.getVerifiedUser());
            sideBar.generateMainSideBar();
            setLoggedInConfig(true);

            new SuccessDialog("An account for " + verifiedUser.getUsername() + " has been created.",
                    "Success");
        }
    }

    protected void onLogOut() {

        verifiedUser = null;
        sideBar = new MainSideBar(this);
        borderPane.setLeft(sideBar.getMainBox());
        setLoggedInConfig(false);

        new SuccessDialog("You have successfully logged out.", "Success");
    }

    public User getVerifiedUser() {

        return verifiedUser;
    }

    public InnerContainer getInnerContainer() {

        return innerContainer;
    }

    protected void onExit() {
        System.exit(0);
    }

    public void updateVerifiedUser(User user) {

        verifiedUser = user;
        sideBar.setVerifiedUser(verifiedUser);
        toolBarHandler.setVerifiedUser(verifiedUser);
    }

    @Override
    public void onStageCloseRequest() {

        System.exit(0);
    }
}
