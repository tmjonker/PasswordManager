package com.tmjonker.passwordmanager.credentials;

import com.tmjonker.passwordmanager.encryption.EncryptionHandler;
import com.tmjonker.passwordmanager.gui.dialog.ExceptionDialog;
import com.tmjonker.passwordmanager.properties.PropertiesHandler;
import com.tmjonker.passwordmanager.users.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class CredentialHandler {

    private final EncryptionHandler encryptionHandler;

    public CredentialHandler() throws GeneralSecurityException {

        encryptionHandler = new EncryptionHandler();
    }

    public Credential finalizeCredential(Credential credential) throws GeneralSecurityException, IOException{

        PropertiesHandler.incrementCredentialsNum();
        credential.setIdentifier(PropertiesHandler.getCredentialsNum());
        credential = encryptCredential(credential);

        return credential;
    }

    public Credential encryptCredential(Credential credential) throws GeneralSecurityException, IOException{

        credential.setPassword(encryptionHandler.encryptCredentials(credential.getUsername(),
                credential.getPassword(), credential.getIdentifier()));
        credential.setKeysetHandleString(encryptionHandler.getKeysetFileString());

        return credential;
    }

    public ObservableList<Credential> generateObservableList(Type type, User user) {

        List<Credential> encryptedList;
        List<Credential> finalEncryptedList = new ArrayList<>();

        if (type != null) // null indicates that "All Passwords" need to be displayed.
            encryptedList = user.getCredentialCollection().get(type);
        else {
            user.getCredentialCollection().forEach((type1, credentials) -> finalEncryptedList.addAll(credentials));
            encryptedList = finalEncryptedList;
        }

        List<Credential> decryptedList = new ArrayList<>();

        for (Credential c : encryptedList) {
            try {
                c.setDecryptedPassword(encryptionHandler.decryptCredentialPassword(c));
                decryptedList.add(c);
            } catch (IOException | GeneralSecurityException ex) {
                new ExceptionDialog(ex);
            }
        }
        return FXCollections.observableArrayList(decryptedList);
    }
}
