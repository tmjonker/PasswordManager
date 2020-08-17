package com.tmjonker.passwordmanager.users;

import com.tmjonker.passwordmanager.credentials.Credential;
import com.tmjonker.passwordmanager.credentials.Type;
import com.tmjonker.passwordmanager.encryption.EncryptionHandler;
import com.tmjonker.passwordmanager.properties.PropertiesHandler;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class used to manipulate User objects.
 *
 * @author Tim Jonker
 */
public class UserHandler {

    private final Logger UH_LOGGER = Logger.getLogger(UserHandler.class.getName());

    private final String USER_FILE_NAME = System.getProperty("user.dir") + "\\users\\user.pm";
    private final File USER_FILE = new File(USER_FILE_NAME);

    private Map<String, User> userHashMap = new HashMap<>();

    private EncryptionHandler encryptionHandler = new EncryptionHandler();

    /**
     * Creates instance of UserHandler and loads the user file from the hard drive.  If file doesn't exist, it
     * creates one.
     *
     * @throws IOException      if there is an issue loading USER_FILE.
     */
    public UserHandler() throws IOException, GeneralSecurityException {

        if (!USER_FILE.exists()) {
            FileOutputStream fileOutputStream = new FileOutputStream(USER_FILE);
            fileOutputStream.flush();
        } else
            loadUserFile();
    }

    public void storeUser(User user) throws IOException {

        userHashMap.put(user.getUsername(), user);
        saveUserFile(userHashMap);
    }

    public User createUser(String username, byte[] password) throws GeneralSecurityException, IOException {

        User newUser = new User(username,null);
        newUser.setIdentifier(getIdentifier());
        newUser.setPassword(encryptionHandler.encryptCredentials(username, password,
                newUser.getIdentifier()));
        newUser.setCredentialCollection(generateCollection());

        return newUser;
    }

    private Map<Type, ArrayList<Credential>> generateCollection() {

        Map<Type, ArrayList<Credential>> collection = new HashMap<>();

        for (Type type : Type.values())
            collection.put(type, new ArrayList<>());

        return collection;
    }

    private int getIdentifier() {

        PropertiesHandler.incrementAccountsNum();
        return PropertiesHandler.getAccountsNum();
    }

    public boolean checkUsernameExists(String username) {

        return userHashMap.containsKey(username);
    }

    private void loadUserFile() throws IOException {

        FileInputStream inputStream = new FileInputStream(USER_FILE);
        ObjectInputStream objectInputStream = null;

        try {
            if (USER_FILE.length() != 0) {
                objectInputStream = new ObjectInputStream(inputStream);
                userHashMap = (HashMap<String, User>) objectInputStream.readObject();
            }
        } catch (EOFException | ClassNotFoundException ex) {
            UH_LOGGER.log(Level.SEVERE, ex.toString(), ex);
        }
    }

    public boolean validateReturningUser(User desiredUser, byte[] attemptedPassword) throws GeneralSecurityException,
            IOException {

        if (encryptionHandler.verifyPassword(desiredUser, attemptedPassword)) {
            return true;
        } else
            return false;
    }

    public User loadUser(String username) {

        return userHashMap.get(username);
    }

    public User updateEncryption(User verifiedUser, byte[] unencryptedPassword) throws GeneralSecurityException, IOException {

        EncryptionHandler encryptionHandler = new EncryptionHandler();
        verifiedUser.setPassword(encryptionHandler.encryptCredentials(verifiedUser.getUsername(),
                unencryptedPassword,
                verifiedUser.getIdentifier())); // re-encrypts password and generates a new Keyset Handle.
        storeUser(verifiedUser);

        return verifiedUser;
    }

    private void saveUserFile(Map<String, User> userHashMap) throws IOException {

        FileOutputStream outputStream = new FileOutputStream(USER_FILE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(userHashMap);
    }

    public void storeCredential(User user, Credential credential) throws IOException {

        Map<Type, ArrayList<Credential>> credentialCollection = user.getCredentialCollection();
        ArrayList<Credential> credentialList = credentialCollection.get(credential.getType());
        credentialList.add(credential);
        credentialCollection.put(credential.getType(), credentialList);
        user.setCredentialCollection(credentialCollection);
        System.out.println("added to " + user.getUsername());

        storeUser(user);
    }
}
