package org.example.tmjonker.PasswordManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class UserHandler {

    private final String USER_FILE_NAME = "users/User.pm";
    private final File USER_FILE = new File(USER_FILE_NAME);

    private Map<String, User> userHashMap = new HashMap<>();

    private int userIdentifier;

    public UserHandler() {

        userIdentifier = PropertiesHandler.getAccountsNum();
    }

    public void storeNewUser(User user) {

        String un = convertUtf8(user.getUsername());

        PropertiesHandler.incrementAccountsNum(userIdentifier + 1);
        userHashMap.put(un, user);
        saveUserFile(userHashMap);
    }

    public boolean checkExists(byte[] username) {

        if (!loadUserFile())
            return false;

        String un = convertUtf8(username);

        return userHashMap.containsKey(un);
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

    public boolean validateReturningUser(User user) {

        if (!loadUserFile()) return false;

        return true;
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
