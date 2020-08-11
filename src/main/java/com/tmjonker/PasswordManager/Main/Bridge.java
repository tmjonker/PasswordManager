package com.tmjonker.PasswordManager.Main;

import javafx.application.Application;
import javafx.stage.Stage;
import com.tmjonker.PasswordManager.GUI.LoginWindow;

public class Bridge extends Application {

    public void start(Stage stage) throws Exception {

        new LoginWindow(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
