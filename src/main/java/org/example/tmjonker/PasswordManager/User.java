package org.example.tmjonker.PasswordManager;

import java.io.Serializable;

public class User implements Serializable {

    private byte[] e_password;
    private byte[] username;

    public User(byte[] username, byte[] password) {
        e_password = password;
        this.username = username;
    }

    public byte[] getE_password() {
        return e_password;
    }

    public byte[] getUsername() {
        return username;
    }
}
