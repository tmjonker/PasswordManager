package com.tmjonker.passwordmanager.gui.window;

import com.tmjonker.passwordmanager.gui.dialog.LoginDialog;
import com.tmjonker.passwordmanager.gui.dialog.NewUserDialog;
import com.tmjonker.passwordmanager.gui.dialog.SuccessDialog;
import com.tmjonker.passwordmanager.gui.sidebar.MainSideBar;
import com.tmjonker.passwordmanager.gui.toolbar.ButtonCreator;
import com.tmjonker.passwordmanager.gui.toolbar.ToolBarHandler;
import com.tmjonker.passwordmanager.users.User;
import com.tmjonker.passwordmanager.users.UserHandler;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.StatusBar;

public class MainWindow implements WindowShell{

    private final String DEFAULT_TITLE = "Password Manager - ";

    private final MenuBar menuBar = new MenuBar();

    private final Menu fileMenu = new Menu("_File");
    private final Menu editMenu = new Menu("_Edit");
    private final Menu accountMenu = new Menu("_Account");
    private final Menu helpMenu = new Menu("_Help");

    private final MenuItem newAccountItem = new MenuItem("_New Account");
    private final MenuItem logInMenuItem = new MenuItem("Log _In");
    private final MenuItem logOutMenuItem = new MenuItem("_Log Out");
    private final MenuItem exitMenuItem = new MenuItem("E_xit Program");

    private Button addButton;
    private Button editButton;
    private Button removeButton;

    private final ToolBar toolBar = new ToolBar();
    private final StatusBar statusBar = new StatusBar();
    private MainSideBar sideBar = new MainSideBar(this);

    private final VBox topVbox = new VBox();
    private final BorderPane borderPane = new BorderPane();
    private InnerContainer innerContainer;

    private final Stage stage = new Stage();

    private User verifiedUser;

    private boolean loggedIn;

    public MainWindow() {

        prepareStage(generateStructure(1000, 600));
    }

    private Scene generateStructure(int width, int height) {

        innerContainer = new InnerContainer(this);
        menuBar.getMenus().addAll(fileMenu, editMenu, accountMenu, helpMenu);
        fileMenu.getItems().addAll(exitMenuItem);
        accountMenu.getItems().addAll(logInMenuItem,newAccountItem,logOutMenuItem);

        exitMenuItem.setOnAction(e -> onExit());
        logInMenuItem.setOnAction(e -> onLogIn());
        newAccountItem.setOnAction(e -> onNewAccount());
        logOutMenuItem.setOnAction(e -> onLogOut());

        topVbox.getChildren().add(menuBar);

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
        addButton.setOnAction(e -> ToolBarHandler.onAddButtonClick(this));
        addButton.setOnMouseEntered(e -> setStatusBarText("Add a new credential"));
        addButton.setOnMouseExited(e -> setStatusBarText(""));

        editButton = ButtonCreator.generateButton(new Image("edit_file_24px.png"));
        editButton.setOnAction(e -> ToolBarHandler.onEditButtonClick(this));
        editButton.setOnMouseEntered(e -> setStatusBarText("Edit the selected credential"));
        editButton.setOnMouseExited(e -> setStatusBarText(""));

        removeButton = ButtonCreator.generateButton(new Image("delete_24px.png"));
        removeButton.setOnAction(e -> ToolBarHandler.onRemoveButtonClick(this));
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
        stage.setResizable(false);
        stage.setOnCloseRequest(e -> onStageCloseRequest());
    }

    protected void setStageTitle(String text) {

        stage.setTitle(DEFAULT_TITLE + text);
    }

    protected void onLogIn() {

        LoginDialog loginDialog = new LoginDialog();

        if (loginDialog.getLoggedIn()) {
            setVerifiedUser(loginDialog.getVerifiedUser());
            sideBar.generateMainSideBar();
            setLoggedInConfig(true);

            new SuccessDialog(verifiedUser.getUsername() + " has been logged in.", "Success");
        }
    }

    protected void setLoggedInConfig(boolean result) {

        loggedIn = result;

        logInMenuItem.setDisable(result);
        newAccountItem.setDisable(result);
        logOutMenuItem.setDisable(!result);

        editButton.setDisable(!result);
        removeButton.setDisable(!result);
        addButton.setDisable(!result);

        if (result) {
            Thread focusGetter = new Thread(new FocusGetter());
            focusGetter.start(); // starts thread that checks for selected table entry to enable/disable edit & remove buttons.
            setStageTitle(verifiedUser.getUsername());
        } else
            setStageTitle("");
    }

    protected void onNewAccount() {

        NewUserDialog newUserDialog = new NewUserDialog();

        if (newUserDialog.newUserCreated()) {
            setVerifiedUser(newUserDialog.getVerifiedUser());
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
        innerContainer.setTableContent(null);
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

    public void setVerifiedUser(User user) {

        verifiedUser = user;
    }

    @Override
    public void onStageCloseRequest() {

        System.exit(0);
    }

    /**
     * Runnable class that is created when focusGetter thread is started.  Checks to see if a row is selected on the
     * table in InnerContainer.  If a row is selected, then the edit and remove buttons are enabled.  If no button is
     * selected, then those buttons are disabled.
     */
    public class FocusGetter implements Runnable {

        @Override
        public void run() {
            while (loggedIn) {
                if (innerContainer.getSelectedRow() == null) {
                    editButton.setDisable(true);
                    removeButton.setDisable(true);
                } else {
                    editButton.setDisable(false);
                    removeButton.setDisable(false);
                }
            }
        }
    }
}