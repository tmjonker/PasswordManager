package org.example.tmjonker.PasswordManager;

import javafx.application.Application;
import javafx.stage.Stage;

public class Bridge extends Application {

    public void start(Stage stage) throws Exception {
        new LoginWindow(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
