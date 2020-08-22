package com.tmjonker.passwordmanager.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHandler {

    static Properties configFile = new Properties();

    private static final String CONFIG_DIRECTORY = System.getProperty("user.dir") + "/config";
    private static final String CONFIG_FILE_NAME = CONFIG_DIRECTORY + "/config.properties";
    private static final File CONFIG_FILE = new File(CONFIG_FILE_NAME);

    private static void loadProperties() {

        try {
            FileInputStream fileStream = new FileInputStream(CONFIG_FILE);
            configFile.load(fileStream);
            fileStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void incrementAccountsNum() {

        loadProperties();
        int accounts = Integer.parseInt(configFile.getProperty("accounts")) + 1;
        configFile.setProperty("accounts", Integer.toString(accounts));
        saveProperties();
    }

    public static void incrementCredentialsNum() {

        loadProperties();
        int credentials = Integer.parseInt(configFile.getProperty("credentials")) + 1;
        configFile.setProperty("credentials", Integer.toString(credentials));
        saveProperties();
    }

    private static void saveProperties() {

        try {
            FileOutputStream outputStream = new FileOutputStream(CONFIG_FILE);
            configFile.store(outputStream, null);
            outputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static int getAccountsNum() {

        loadProperties();
        return Integer.parseInt(configFile.getProperty("accounts"));
    }

    public static int getCredentialsNum() {

        loadProperties();
        return Integer.parseInt(configFile.getProperty("credentials"));
    }
}
