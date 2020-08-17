package com.tmjonker.passwordmanager.gui.toolbar;

import com.tmjonker.passwordmanager.gui.dialog.AddCredentialDialog;
import com.tmjonker.passwordmanager.gui.window.InnerContainer;
import com.tmjonker.passwordmanager.gui.window.MainWindow;
import com.tmjonker.passwordmanager.users.User;

public class ToolBarHandler {

    private User verifiedUser;

    MainWindow mainWindow;

    public ToolBarHandler(MainWindow window) {

        mainWindow = window;
    }

    public void onAddButtonClick() {

        AddCredentialDialog addDialog = new AddCredentialDialog(verifiedUser);

        if (addDialog.addedSuccessfully()) {
            mainWindow.updateVerifiedUser(addDialog.getVerifiedUser());
        }
    }

    public void onEditButtonClick() {


    }

    public void onRemoveButtonClick() {


    }

    public void setVerifiedUser(User user) {

        verifiedUser = user;
    }
}
