package org.example.tmjonker.PasswordManager.Properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHandler {

    static Properties configFile = new Properties();

    private static final String CONFIG_FILE_NAME = "config.properties";
    private static final File CONFIG_FILE = new File(CONFIG_FILE_NAME);

    private static void loadProperties() {

        try {
            FileInputStream fileStream = new FileInputStream(CONFIG_FILE);
            configFile.load(fileStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void incrementAccountsNum(int accountsNum) {

        loadProperties();
        configFile.setProperty("accounts", Integer.toString(accountsNum));
        saveProperties();
    }

    private static void saveProperties() {

        try {
            FileOutputStream outputStream = new FileOutputStream(CONFIG_FILE);
            configFile.store(outputStream, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static int getAccountsNum() {

        loadProperties();
        return Integer.parseInt(configFile.getProperty("accounts"));
    }
}
