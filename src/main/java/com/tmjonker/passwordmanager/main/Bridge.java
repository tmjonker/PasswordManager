package com.tmjonker.passwordmanager.main;

import javafx.application.Application;
import javafx.stage.Stage;
import com.tmjonker.passwordmanager.gui.window.LoginWindow;

public class Bridge extends Application {

    public void start(Stage stage) throws Exception {

        new LoginWindow(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
