package com.tmjonker.passwordmanager.users;

import com.tmjonker.passwordmanager.credentials.Credential;
import com.tmjonker.passwordmanager.credentials.Type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {

    private byte[] password; //encrypted password
    private final String username;
    private Map<Type, ArrayList<Credential>> credentialCollection = new HashMap<>();

    private int identifier; // unique identifier. Used to link user to password list.

    public User(String username, byte[] password) {
        this.password = password;
        this.username = username;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {

        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public int getIdentifier() {

        return identifier;
    }

    public void setIdentifier(int num) {

        identifier = num;
    }
}
