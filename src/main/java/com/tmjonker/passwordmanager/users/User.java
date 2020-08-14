package com.tmjonker.passwordmanager.users;

import com.google.crypto.tink.KeysetHandle;

import java.io.Serializable;

public class User implements Serializable {

    private byte[] password; //encrypted password
    private final byte[] username;
    private KeysetHandle kh;

    private int identifier; // unique identifier. Used to link user to password list.

    public User(byte[] username, byte[] password) {
        this.password = password;
        this.username = username;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {

        this.password = password;
    }

    public byte[] getUsername() {
        return username;
    }

    public int getIdentifier() {

        return identifier;
    }

    public void setIdentifier(int num) {

        identifier = num;
    }
}
