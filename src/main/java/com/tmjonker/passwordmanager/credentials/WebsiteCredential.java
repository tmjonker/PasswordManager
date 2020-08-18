package com.tmjonker.passwordmanager.credentials;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class WebsiteCredential implements Credential, Serializable {

    private URI url;
    private String username;
    private byte[] password;
    private Type type;
    private String identifier;
    private String decryptedPassword;

    public WebsiteCredential(String url, String username, String password) {

        try {
            this.url = new URI(url);
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        this.username = username;
        this.password = password.getBytes();
        this.type = Type.WEBSITE;
    }

    public void setUrl(String url) throws URISyntaxException {

        this.url = new URI(url);
    }

    public URI getUrl() {

        return url;
    }

    @Override
    public void setUsername(String username) {

        this.username = username;
    }

    @Override
    public void setPassword(byte[] password) {

        this.password = password;
    }

    @Override
    public void setType(Type type) {

        this.type = type;
    }

    @Override
    public void setIdentifier(int identifier) {

        this.identifier = "c" + identifier;
    }

    @Override
    public void setDecryptedPassword(byte[] password) {

        decryptedPassword = convertUtf8(password);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public byte[] getPassword() {
        return password;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getDecryptedPassword() {
        return decryptedPassword;
    }

    private String convertUtf8(byte[] input) {

        return new String(input, StandardCharsets.UTF_8);
    }
}
