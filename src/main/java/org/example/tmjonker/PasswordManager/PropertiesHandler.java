package org.example.tmjonker.PasswordManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHandler {

    Properties configFile = new Properties();

    private final String CONFIG_FILE_NAME = "config.properties";
    private final File CONFIG_FILE = new File(CONFIG_FILE_NAME);

    public PropertiesHandler() {

        loadProperties();
    }

    private void loadProperties() {

        try {
            FileInputStream fileStream = new FileInputStream(CONFIG_FILE);
            configFile.load(fileStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void incrementAccountsNum(int accountsNum) {

        configFile.setProperty("accounts", Integer.toString(accountsNum));
        saveProperties();
    }

    private void saveProperties() {

        try {
            FileOutputStream outputStream = new FileOutputStream(CONFIG_FILE);
            configFile.store(outputStream, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int getAccountsNum() {

        return Integer.parseInt(configFile.getProperty("accounts"));
    }
}
