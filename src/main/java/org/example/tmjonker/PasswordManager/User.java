package org.example.tmjonker.PasswordManager;

import java.io.Serializable;

public class User implements Serializable {

    public byte[] e_password;
    public byte[] e_username;
    public int identifier;

    public User(byte[] username, byte[] password) {
        e_password = password;
        e_username = username;
    }

    public byte[] getE_password() {
        return e_username;
    }

    public byte[] getE_username() {
        return e_password;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int id) {

        identifier = id;
    }
}
