package org.example.tmjonker.PasswordManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class UserHandler {

    private final String USER_FILE_NAME = "users/User.pm";
    private final File USER_FILE = new File(USER_FILE_NAME);

    private Map<Integer, User> userHashMap = new HashMap<>();

    private int nextUserIdentifier;

    public UserHandler(String username, String password) {

    }

    public UserHandler(User user) {

        PropertiesHandler propertiesHandler = new PropertiesHandler();
        nextUserIdentifier = propertiesHandler.getAccountsNum() + 1;
        propertiesHandler.incrementAccountsNum(nextUserIdentifier);
        storeNewUser(user);
    }

    private void storeNewUser(User user) {

        userHashMap.put(user.getIdentifier(), user);
        saveUserFile(userHashMap);
    }

    public void loadReturningUser() {


    }

    private void saveUserFile(Map<Integer, User> userHashMap) {

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
