package org.example.tmjonker.PasswordManager;

import java.io.Serializable;

public class User implements Serializable {

    private final byte[] password;
    private final String username;

    public User(byte[] password, String username) {
        this.password = password;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public byte[] getPassword() {
        return password;
    }
}
