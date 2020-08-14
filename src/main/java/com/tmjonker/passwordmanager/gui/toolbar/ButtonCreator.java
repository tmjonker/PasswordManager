package com.tmjonker.passwordmanager.gui.toolbar;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ButtonCreator {

    public static Button generateButton(Image image) {

        Button button = new Button();
        button.setGraphic(new ImageView(image));

        return button;
    }
}
