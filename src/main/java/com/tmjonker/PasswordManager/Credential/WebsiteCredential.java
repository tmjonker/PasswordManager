package com.tmjonker.PasswordManager.Credential;

import java.net.URI;
import java.net.URISyntaxException;

public class WebsiteCredential extends Credential {

    private URI url;

    public WebsiteCredential(String url, String username, String password) throws URISyntaxException {

        this.url = new URI(url);
        super.username = username.getBytes();
        super.e_password = password.getBytes();
        super.type = Type.WEBSITE;
    }

    public void setWebURL(String url) throws URISyntaxException {

        this.url = new URI(url);
    }

    public URI getWebURL() {

        return url;
    }
}
