package org.example.tmjonker.PasswordManager;

import com.google.crypto.tink.*;
import com.google.crypto.tink.aead.AesGcmKeyManager;
import com.google.crypto.tink.config.TinkConfig;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

public class TinkPasswordVault {

    String keysetFileName;

    public TinkPasswordVault() {

        keysetFileName = "keysets/";
    }

    public User encryptCredentials(byte[] username, byte[] password)
            throws GeneralSecurityException, IOException {
        TinkConfig.register();
        KeysetHandle keysetHandle = KeysetHandle.generateNew(AesGcmKeyManager.aes128GcmTemplate());

        saveKeysetHandle(keysetHandle, username);

        Aead aead = keysetHandle.getPrimitive(Aead.class);

        byte[] e_pw = aead.encrypt(password, username);

        return new User(username, e_pw);
    }

    private void saveKeysetHandle(KeysetHandle keysetHandle, byte[] username) {

        keysetFileName = keysetFileName + username + ".json";

        try {
            CleartextKeysetHandle.write(keysetHandle, JsonKeysetWriter.withFile(new File(keysetFileName)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void saveCipherText(User user) {
        try {
            String filename = user.getUsername() + ".pm";
            FileOutputStream fileOutputStream =
                    new FileOutputStream(new File(filename));
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(user);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private KeysetHandle loadKeysetHandle() throws GeneralSecurityException, IOException {

        return CleartextKeysetHandle.read(JsonKeysetReader.withFile(new File(keysetFileName)));
    }

    private User loadUserLogin(String username) {
        User user = null;
        try {
            String filename = username + ".pm";
            FileInputStream fileInputStream = new FileInputStream(new File(filename));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            user = (User) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    public boolean verifyPassword(byte[] username, byte[] password) throws GeneralSecurityException, IOException {
        KeysetHandle keysetHandle = loadKeysetHandle();

        Aead aead = keysetHandle.getPrimitive(Aead.class);
        //byte[] decrypted = aead.decrypt(user.getE_password(), user.getE_username());
        return true;
    }
}
