package com.tmjonker.passwordmanager.credentials;

import com.tmjonker.passwordmanager.encryption.EncryptionHandler;
import com.tmjonker.passwordmanager.encryption.StringEncoder;
import com.tmjonker.passwordmanager.gui.dialog.ExceptionDialog;
import com.tmjonker.passwordmanager.properties.PropertiesHandler;
import com.tmjonker.passwordmanager.users.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CredentialHandler {

    private final EncryptionHandler encryptionHandler;

    public CredentialHandler() throws GeneralSecurityException {

        encryptionHandler = new EncryptionHandler();
    }

    public void finalizeCredential(Credential credential) throws GeneralSecurityException, IOException{

        PropertiesHandler.incrementCredentialsNum();
        credential.setIdentifier(PropertiesHandler.getCredentialsNum());
        encryptCredential(credential);
    }

    public void encryptCredential(Credential credential) throws GeneralSecurityException, IOException{

        credential.setPassword(encryptionHandler.encryptCredentials(credential.getUsername(),
                credential.getPassword(), credential.getIdentifier()));
        credential.setKeysetHandleString(encryptionHandler.getKeysetFileString());
    }

    public void clearDecryptedPasswords(Map<Type, ArrayList<Credential>> credMap) {

        credMap.forEach((type, list) -> {
            for (Credential c : list) {
                c.setDecryptedPassword(null);
            }
        });
    }

    public ObservableList<Credential> generateObservableList(Type type, Map<Type, ArrayList<Credential>> credMap) throws IOException,
            GeneralSecurityException {

        List<Credential> encryptedList;
        List<Credential> finalEncryptedList = new ArrayList<>();

        if (type != null) // null indicates that "All Passwords" need to be displayed.
            encryptedList = credMap.get(type);
        else {
            credMap.forEach((type1, credentials) -> finalEncryptedList.addAll(credentials));
            encryptedList = finalEncryptedList;
        }

        List<Credential> decryptedList = new ArrayList<>();

        for (Credential c : encryptedList) {
            String password = StringEncoder.convertUtf8(encryptionHandler.decryptCredentialPassword(c));
            c.setDecryptedPassword(password);
            decryptedList.add(c);
        }
        return FXCollections.observableArrayList(decryptedList);
    }
}
