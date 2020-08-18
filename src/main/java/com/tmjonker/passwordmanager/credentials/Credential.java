package com.tmjonker.passwordmanager.credentials;

public interface Credential {

 void setUsername(String username);

 void setPassword(byte[] password);

 void setType(Type type);

 void setIdentifier(int identifier);

 void setDecryptedPassword(byte[] password);

 String getUsername();

 byte[] getPassword();

 Type getType();

 String getIdentifier();

 String getDecryptedPassword();
}
