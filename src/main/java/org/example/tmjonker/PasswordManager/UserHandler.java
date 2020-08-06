package org.example.tmjonker.PasswordManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class UserHandler {

    private final String CONFIG_FILE_NAME = "config.properties";
    Properties properties = new Properties();

    public UserHandler(String username, String password) {

        loadProperties();
        loadUser();
    }

    public UserHandler(User user) {

        storeNewUser(user);
    }

    private void storeNewUser(User user) {

        int accountsNum = getAccountsNum()+1;
        user.setIdentifier(accountsNum);
        loadProperties();
        properties.setProperty("username." + accountsNum, convertUtf8(user.getUsername()));
        properties.setProperty("password." + accountsNum, convertUtf8(user.getPassword()));
        incrementAccountsNum(accountsNum);
        saveProperties();
    }

    public void loadUser() {


    }

    private void loadProperties() {

        try {
            FileInputStream fileStream = new FileInputStream(new File(CONFIG_FILE_NAME));
            properties.load(fileStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private int getAccountsNum() {

        String num = properties.getProperty("accounts");
        return Integer.parseInt(num);
    }

    private void incrementAccountsNum(int accountsNum) {

        properties.setProperty("accounts", Integer.toString(accountsNum));
    }

    private void saveProperties() {

        try {
            FileOutputStream outputStream = new FileOutputStream(new File(CONFIG_FILE_NAME));
            properties.store(outputStream, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private String convertUtf8(byte[] input) {

        return new String(input, StandardCharsets.UTF_8);
    }
}
