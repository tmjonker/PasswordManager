package org.example.tmjonker.PasswordManager;

public class Credential {

 protected byte[] username;
 protected byte[] e_password;
 protected Type type;

 public Credential() {

 }

 public Credential(byte[] username, byte[] password) {

  this.username = username;
  e_password = password;
 }

 public void setUsername(byte[] username) {

  this.username = username;
 }

 public void setE_password(byte[] password) {

  e_password = password;
 }

 public void setType(Type type) {

  type = type;
 }

 public byte[] get_username() {

  return username;
 }

 public byte[] getE_password() {

  return e_password;
 }

 public Type getType() {

  return type;
 }
}
