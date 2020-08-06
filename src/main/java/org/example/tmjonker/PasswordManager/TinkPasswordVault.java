package org.example.tmjonker.PasswordManager;

import com.google.crypto.tink.*;
import com.google.crypto.tink.aead.AesGcmKeyManager;
import com.google.crypto.tink.config.TinkConfig;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

public class TinkPasswordVault {

    public static User encryptCredentials(byte[] username, byte[] password)
            throws GeneralSecurityException, IOException {
        TinkConfig.register();
        KeysetHandle keysetHandle = KeysetHandle.generateNew(AesGcmKeyManager.aes128GcmTemplate());

        saveKeysetHandle(keysetHandle, username);

        Aead aead = keysetHandle.getPrimitive(Aead.class);

        byte[] e_pw = aead.encrypt(password, username);
        byte[] e_un = aead.encrypt(username, password);

        return new User(e_un, e_pw);
    }

    private static void saveKeysetHandle(KeysetHandle keysetHandle, byte[] username) throws IOException {
        String keysetFileName = username + ".json";
        CleartextKeysetHandle.write(keysetHandle, JsonKeysetWriter.withFile(new File(keysetFileName)));
    }

    private static void saveCipherText(User user) {
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

    private static KeysetHandle loadKeysetHandle(String username) throws GeneralSecurityException, IOException {
        String keysetFileName = username + ".json";

        return CleartextKeysetHandle.read(JsonKeysetReader.withFile(new File(keysetFileName)));
    }

    private static User loadUserLogin(String username) {
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

    public static String verifyPassword(String username) throws GeneralSecurityException, IOException {
        KeysetHandle keysetHandle = loadKeysetHandle(username);
        User user = loadUserLogin(username);

        Aead aead = keysetHandle.getPrimitive(Aead.class);
        byte[] decrypted = aead.decrypt(user.getPassword(), user.getUsername());
        return new String(decrypted, StandardCharsets.UTF_8);
    }
}
