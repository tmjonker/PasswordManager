package com.tmjonker.passwordmanager.users;

import com.tmjonker.passwordmanager.encryption.EncryptionHandler;
import com.tmjonker.passwordmanager.properties.PropertiesHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

/**
 * The class used to manipulate User objects.
 *
 * @author Tim Jonker
 */
public class UserHandler {

    /**
     * @param USER_FILE_NAME        the path and filename for the file that stores userHashMap.
     * @param USER_FILE             the file representation of the file that stores userHashMap.
     * @param userHashMap           the collection that all created Users are stored in.
     * @param nextUserIdentifier    the unique identifier for the next User to be created.  Based on the total
     *                              number of Users that have been created.
     */
    private final String USER_FILE_NAME = System.getProperty("user.dir") + "\\users\\user.pm";
    private final File USER_FILE = new File(USER_FILE_NAME);
    private Map<String, User> userHashMap = new HashMap<>();
    private int nextUserIdentifier;

    public UserHandler() throws IOException {

        loadUserFile();
    }

    /**
     * Creates a new User and stores that User object in the userHashMap collection.
     * @param username                      the username for the account that was typed in by the user of the program.
     * @param password                      the password for the account that was typed in by the user of the program.
     * @throws GeneralSecurityException     if unable to encrypt password using the EncryptionHandler.
     */
    public void storeNewUser(byte[] username, byte[] password) throws GeneralSecurityException, IOException {

        nextUserIdentifier = PropertiesHandler.getAccountsNum() + 1;
        PropertiesHandler.incrementAccountsNum(nextUserIdentifier);

        User user = new User(username, null);
        user.setIdentifier(nextUserIdentifier);
        EncryptionHandler encryptionHandler = new EncryptionHandler();
        user.setE_password(encryptionHandler.encryptCredentials(username, password, user.getIdentifier()));

        String usernameString = convertUtf8(username);

        userHashMap.put(usernameString, user);
        saveUserFile(userHashMap);
    }

    /**
     * Checks to see if username that was typed in already exists in the userHashMap collection.
     * @param username      the username to be checked.
     * @return              true if the username is available for creation; false if username is not available or if
     *                      the users.pm file could not be loaded.
     */
    public boolean checkUsernameAvailability(String username) throws IOException {

        loadUserFile();

        return userHashMap.containsKey(username);

    }

    /**
     * Loads the
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void loadUserFile() throws IOException {

        FileInputStream inputStream = new FileInputStream(USER_FILE);
        ObjectInputStream objectInputStream = null;

        try {
            objectInputStream = new ObjectInputStream(inputStream);
            userHashMap = (HashMap<String, User>) objectInputStream.readObject();
        } catch (EOFException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public boolean validateReturningUser(String username, byte[] password) throws GeneralSecurityException,
            IOException {

        loadUserFile();

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
