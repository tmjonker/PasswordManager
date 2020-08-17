package com.tmjonker.passwordmanager.credentials;

import java.io.Serializable;

public class Credential implements Serializable {

 protected String username;
 protected byte[] password;
 protected Type type;

 public void setUsername(String username) {

  this.username = username;
 }

 public void setPassword(byte[] password) {

  this.password = password;
 }

 public void setType(Type type) {

  this.type = type;
 }

 public String getUsername() {

  return username;
 }

 public byte[] getPassword() {

  return password;
 }

 public Type getType() {

  return type;
 }
}
