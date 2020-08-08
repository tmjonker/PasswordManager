package org.example.tmjonker.PasswordManager;

import java.net.URI;
import java.net.URISyntaxException;

public class WebsiteCredential extends Credential {

    private URI webURL;

    public WebsiteCredential(String url, String username, String password) throws URISyntaxException {

        webURL = new URI(url);
        super.username = username.getBytes();
        super.e_password = password.getBytes();
        super.type = Type.WEBSITE;
    }

    public void setWebURL(String url) throws URISyntaxException {

        webURL = new URI(url);
    }

    public URI getWebURL() {

        return webURL;
    }
}
