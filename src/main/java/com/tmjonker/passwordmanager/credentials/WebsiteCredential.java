package com.tmjonker.passwordmanager.credentials;

import java.net.URI;
import java.net.URISyntaxException;

public class WebsiteCredential extends Credential {

    private URI url;

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

    public void setWebURL(String url) throws URISyntaxException {

        this.url = new URI(url);
    }

    public URI getWebURL() {

        return url;
    }
}
