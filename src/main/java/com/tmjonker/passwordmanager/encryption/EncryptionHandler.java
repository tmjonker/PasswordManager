package com.tmjonker.passwordmanager.encryption;

import com.google.crypto.tink.*;
import com.google.crypto.tink.aead.AesGcmKeyManager;
import com.google.crypto.tink.config.TinkConfig;
import com.tmjonker.passwordmanager.credentials.Credential;
import com.tmjonker.passwordmanager.users.User;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Arrays;

/**
 * The class that handles all of the password encryption for PasswordManager.
 *
 * @author Tim Jonker
 */
public class EncryptionHandler {

    private final String KEYSET_FILE_PATH;

    public EncryptionHandler() throws GeneralSecurityException {

        KEYSET_FILE_PATH = System.getProperty("user.dir") + "/keysets/";
        TinkConfig.register();
    }

    public byte[] encryptCredentials(String username, byte[] password, String identifier)
            throws GeneralSecurityException, IOException {

        KeysetHandle keysetHandle = KeysetHandle.generateNew(AesGcmKeyManager.aes128GcmTemplate());
        saveKeysetHandle(keysetHandle, identifier);
        Aead aead = keysetHandle.getPrimitive(Aead.class);

        return aead.encrypt(password, username.getBytes());
    }

    private void saveKeysetHandle(KeysetHandle keysetHandle, String identifier) throws IOException {

        String keysetFileName = KEYSET_FILE_PATH + identifier + ".json";
        File keysetFile = new File(keysetFileName);

        CleartextKeysetHandle.write(keysetHandle, JsonKeysetWriter.withFile(keysetFile));
    }

    private KeysetHandle loadKeysetHandle(String identifier) throws GeneralSecurityException, IOException {

        String keysetFileName = KEYSET_FILE_PATH + identifier + ".json";
        File keysetFile = new File(keysetFileName);

        return CleartextKeysetHandle.read(JsonKeysetReader.withFile(keysetFile));
    }

    public boolean verifyPassword(User user, byte[] password) throws GeneralSecurityException,
            IOException {

        KeysetHandle keysetHandle = loadKeysetHandle(user.getIdentifier());

        Aead aead = keysetHandle.getPrimitive(Aead.class);
        byte[] decrypted = aead.decrypt(user.getPassword(), user.getUsername().getBytes());

        return Arrays.equals(decrypted, password);
    }

    public byte[] decryptCredentialPassword(Credential credential) throws GeneralSecurityException, IOException{

        KeysetHandle keysetHandle = loadKeysetHandle(credential.getIdentifier());

        Aead aead = keysetHandle.getPrimitive(Aead.class);

        return aead.decrypt(credential.getPassword(), credential.getUsername().getBytes());
    }

    private String convertUtf8(byte[] input) {

        return new String(input, StandardCharsets.UTF_8);
    }
}
