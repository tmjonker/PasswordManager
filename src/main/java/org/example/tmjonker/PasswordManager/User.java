package org.example.tmjonker.PasswordManager;

import java.io.Serializable;

public class User implements Serializable {

    private final byte[] e_password;
    private final byte[] e_username;
    private int identifier;

    public User(byte[] username, byte[] password) {
        e_password = password;
        e_username = username;
    }

    public byte[] getUsername() {
        return e_username;
    }

    public byte[] getPassword() {
        return e_password;
    }

    public void setIdentifier(int num) {

        identifier = num;
    }
}
