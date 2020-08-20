package com.tmjonker.passwordmanager.gui.toolbar;

import com.tmjonker.passwordmanager.gui.dialog.AddCredentialDialog;
import com.tmjonker.passwordmanager.gui.dialog.EditCredentialDialog;
import com.tmjonker.passwordmanager.gui.dialog.RemoveCredentialDialog;
import com.tmjonker.passwordmanager.gui.window.InnerContainer;
import com.tmjonker.passwordmanager.gui.window.MainWindow;
import com.tmjonker.passwordmanager.users.User;

public class ToolBarHandler {

    public static void onAddButtonClick(MainWindow mainWindow) {

        new AddCredentialDialog(mainWindow);
    }

    public static void onEditButtonClick(MainWindow mainWindow) {

        new EditCredentialDialog(mainWindow);
    }

    public static void onRemoveButtonClick(MainWindow mainWindow) {

        new RemoveCredentialDialog(mainWindow);
    }
}
