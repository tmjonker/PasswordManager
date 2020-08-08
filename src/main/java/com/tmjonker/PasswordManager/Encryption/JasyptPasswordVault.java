package com.tmjonker.PasswordManager.Encryption;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;

public class JasyptPasswordVault {

    private final String username;
    private final String password;

    public JasyptPasswordVault(String un, String pw) {

        username = encryptUsername(un);
        password = encryptPassword(pw);

        storeCredentials(username, password);
    }

    private String encryptUsername(String un) {

        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword("jubilee");
;        return basicTextEncryptor.encrypt(un);
    }

    private String encryptPassword(String pw) {

        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword("jubilee");
        return basicTextEncryptor.encrypt(pw);
    }

    private void storeCredentials(String un, String pw) {

        //new AccountHandler(un, pw);
    }

    public String getUsername() {

        return username;
    }

    public String getPassword() {

        return password;
    }
}
