package org.example.tmjonker.PasswordManager;

import com.google.crypto.tink.*;
import com.google.crypto.tink.aead.AesGcmKeyManager;
import com.google.crypto.tink.config.TinkConfig;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Arrays;

public class TinkPasswordVault {

    String keysetFileName;

    public TinkPasswordVault() throws GeneralSecurityException{

        keysetFileName = "keysets/";
        TinkConfig.register();
    }

    public byte[] encryptCredentials(byte[] username, byte[] password)
            throws GeneralSecurityException, IOException {

        KeysetHandle keysetHandle = KeysetHandle.generateNew(AesGcmKeyManager.aes128GcmTemplate());

        //saveKeysetHandle(keysetHandle, username);

        Aead aead = keysetHandle.getPrimitive(Aead.class);

        return aead.encrypt(password, username);
    }

    public byte[] encryptCredentials(User user, byte[] password)
            throws GeneralSecurityException, IOException {

        TinkConfig.register();
        KeysetHandle keysetHandle = KeysetHandle.generateNew(AesGcmKeyManager.aes128GcmTemplate());

        saveKeysetHandle(keysetHandle, user.getIdentifier());

        Aead aead = keysetHandle.getPrimitive(Aead.class);

        return aead.encrypt(password, user.getUsername());
    }
    /*
    saveKeysetHandle:
    Keysets are named based on the identifier contained in each User object.
     */
    private void saveKeysetHandle(KeysetHandle keysetHandle, int identifier) {

        keysetFileName = keysetFileName + identifier + ".json";

        try {
            CleartextKeysetHandle.write(keysetHandle, JsonKeysetWriter.withFile(new File(keysetFileName)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private KeysetHandle loadKeysetHandle(int identifier) throws GeneralSecurityException, IOException {

        keysetFileName = keysetFileName + identifier + ".json";

        return CleartextKeysetHandle.read(JsonKeysetReader.withFile(new File(keysetFileName)));
    }

    /*
    verifyPassword:
    Method argument password is the password entered by the user when they try to log in.  It is compared to the
    password that is stored in the User argument.
    */
    public boolean verifyPassword(User user, byte[] password) throws GeneralSecurityException, IOException {

        KeysetHandle keysetHandle = loadKeysetHandle(user.getIdentifier());

        Aead aead = keysetHandle.getPrimitive(Aead.class);
        byte[] decrypted = aead.decrypt(user.getE_password(), user.getUsername());

        return Arrays.equals(decrypted, password);
    }
}
