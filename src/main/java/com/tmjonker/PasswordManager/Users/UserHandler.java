package com.tmjonker.PasswordManager.Users;

import com.tmjonker.PasswordManager.Encryption.EncryptionHandler;
import com.tmjonker.PasswordManager.Properties.PropertiesHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

public class UserHandler {

    private final String USER_FILE_NAME = "users/User.pm";
    private final File USER_FILE = new File(USER_FILE_NAME);

    private Map<String, User> userHashMap = new HashMap<>();

    private int nextUserIdentifier;

    /*
    storeNewUser:
    Creates new User object, creates and sets the identifier variable for the User, encrypt's the user's password,
    and stores the User object in a hashmap that is saved to disk.
     */
    public void storeNewUser(byte[] username, byte[] password) throws IOException, GeneralSecurityException {

        nextUserIdentifier = PropertiesHandler.getAccountsNum() + 1;

        EncryptionHandler encryptionHandler = new EncryptionHandler();

        User user = new User(username, null);
        user.setIdentifier(nextUserIdentifier);
        user.setE_password(encryptionHandler.encryptCredentials(username, password, user.getIdentifier()));

        String usernameString = convertUtf8(username);

        PropertiesHandler.incrementAccountsNum(nextUserIdentifier);

        userHashMap.put(usernameString, user);
        saveUserFile(userHashMap);
    }

    public boolean checkExists(String username) {

        if (!loadUserFile())
            return false;

        return userHashMap.containsKey(username);
    }

    private boolean loadUserFile() {

        try {
            FileInputStream inputStream = new FileInputStream(USER_FILE);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            userHashMap = (HashMap<String, User>) objectInputStream.readObject();
            return true;
        } catch (IOException | ClassNotFoundException ex) {

            ex.printStackTrace();
        }
        return false;
    }

    public boolean validateReturningUser(String username, byte[] password)
            throws IOException, GeneralSecurityException {

        if (!loadUserFile()) return false;

        User user = userHashMap.get(username);
        EncryptionHandler encryptionHandler = new EncryptionHandler();

        return encryptionHandler.verifyPassword(user, password);
    }

    private void saveUserFile(Map<String, User> userHashMap) {

        try {
            FileOutputStream outputStream = new FileOutputStream(USER_FILE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(userHashMap);
        } catch (IOException ex) {

            ex.printStackTrace();
        }
    }

    private String convertUtf8(byte[] input) {

        return new String(input, StandardCharsets.UTF_8);
    }
}
