package com.tmjonker.passwordmanager.gui.toolbar;

import com.tmjonker.passwordmanager.gui.dialog.AddCredentialDialog;
import com.tmjonker.passwordmanager.users.User;
import com.tmjonker.passwordmanager.users.UserHandler;

public class ToolBarHandler {

    private User verifiedUser;

    public ToolBarHandler(User user) {

        verifiedUser = user;
    }

    public void onAddButtonClick() {

        new AddCredentialDialog(verifiedUser);
    }

    public void onEditButtonClick() {


    }

    public void onRemoveButtonClick() {


    }

    public void setVerifiedUser(User user) {

        verifiedUser = user;
    }
}
