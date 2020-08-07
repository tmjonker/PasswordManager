package org.example.tmjonker.PasswordManager;

import java.io.Serializable;

public class User implements Serializable {

    private byte[] e_password;
    private byte[] username;

    private int identifier;

    public User(byte[] username, byte[] password) {
        e_password = password;
        this.username = username;
    }

    public byte[] getE_password() {
        return e_password;
    }

    public void setE_password(byte[] password) {

        e_password = password;
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
