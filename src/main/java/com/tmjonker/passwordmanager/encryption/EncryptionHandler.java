package com.tmjonker.passwordmanager.encryption;

import com.google.crypto.tink.*;
import com.google.crypto.tink.aead.AesGcmKeyManager;
import com.google.crypto.tink.config.TinkConfig;
import com.tmjonker.passwordmanager.users.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Arrays;

/**
 * The class that handles all of the password encryption for PasswordManager.
 *
 * @author Tim Jonker
 */
public class EncryptionHandler {

    /**
     * @param keysetFileName     the file path for the KeysetHandle object used for encryption.
     */
    String keysetFileName;

    /**
     * Creates instance of EncryptionHandler, generates folder path that will be used to store
     * the KeysetHandle, and registers TinkConfig to enable encryption/decryption.
     * @throws GeneralSecurityException
     */
    public EncryptionHandler() throws GeneralSecurityException {

        keysetFileName = System.getProperty("user.dir") + "\\keysets\\";
        TinkConfig.register();
    }

    /**
     * Encrypts password using AES 128.
     * @param username      the username for the credential.
     * @param password      the password that was entered by the user for the credential.
     * @param identifier    the unique identifier associated with the user.
     * @return              returns an encrypted version of the password entered by the user.
     * @throws GeneralSecurityException
     */
    public byte[] encryptCredentials(byte[] username, byte[] password, int identifier)
            throws GeneralSecurityException, IOException {

        KeysetHandle keysetHandle = KeysetHandle.generateNew(AesGcmKeyManager.aes128GcmTemplate());
        saveKeysetHandle(keysetHandle, identifier);
        Aead aead = keysetHandle.getPrimitive(Aead.class);

        return aead.encrypt(password, username);
    }

    /**
     * Saves the KeysetHandle to local disk.
     * @param keysetHandle  the KeysetHandle that is to be stored on local disk drive.
     * @param identifier    the unique identifier associated with the user that is used as the name of the file.
     */
    private void saveKeysetHandle(KeysetHandle keysetHandle, int identifier) throws IOException {

        Path dirPath = Paths.get(keysetFileName);
        keysetFileName = keysetFileName + identifier + ".json";
        File keysetFile = new File(keysetFileName);

        CleartextKeysetHandle.write(keysetHandle, JsonKeysetWriter.withFile(keysetFile));
        Files.setAttribute(dirPath, "dos:hidden",
                true, LinkOption.NOFOLLOW_LINKS); // hides the keysetFile after stored on disk.

    }

    /**
     * Loads the KeysetHandle that was stored on the local disk drive.
     * @param identifier                    the unique identifier of the user.
     * @return                              returns the KeysetHandle object that was loaded from disk storage.
     * @throws GeneralSecurityException
     * @throws IOException
     */
    private KeysetHandle loadKeysetHandle(int identifier) throws GeneralSecurityException, IOException {

        keysetFileName = keysetFileName + identifier + ".json";
        File keysetFile = new File(keysetFileName);

        return CleartextKeysetHandle.read(JsonKeysetReader.withFile(keysetFile));
    }

    /**
     * Checks to see if password entered by user matches the password stored on disk.
     * @param user      the User object representing the user that wants to log in.
     * @param password  the password entered by the user trying to login.
     * @return          returns true if passwords match; false if they do not match.
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public boolean verifyPassword(User user, byte[] password) throws GeneralSecurityException,
            IOException {

        KeysetHandle keysetHandle = loadKeysetHandle(user.getIdentifier());

        Aead aead = keysetHandle.getPrimitive(Aead.class);
        byte[] decrypted = aead.decrypt(user.getE_password(), user.getUsername());

        return Arrays.equals(decrypted, password);
    }
}
