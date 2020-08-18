package com.tmjonker.passwordmanager.credentials;

import com.tmjonker.passwordmanager.encryption.EncryptionHandler;
import com.tmjonker.passwordmanager.properties.PropertiesHandler;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class CredentialHandler {

    EncryptionHandler encryptionHandler;
    PropertiesHandler propertiesHandler;

    public CredentialHandler() throws GeneralSecurityException {

        encryptionHandler = new EncryptionHandler();
    }

    public Credential finalizeCredential(Credential credential) throws GeneralSecurityException, IOException{

        PropertiesHandler.incrementCredentialsNum();

        credential.setIdentifier(PropertiesHandler.getCredentialsNum());

        return encryptCredential(credential);
    }

    public Credential encryptCredential(Credential credential) throws GeneralSecurityException, IOException{

        credential.setPassword(encryptionHandler.encryptCredentials(credential.getUsername(),
                credential.getPassword(), credential.getIdentifier()));

        return credential;
    }

}
