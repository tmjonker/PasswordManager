# Password Manager

This is a project utilizing the Google Tink library.  I made this to experiment with encryption and to get a feel for incorporating more inheritance and polymorphism into my work.  Right now, the program will allow you to create a new user and then log in as that user. When you create a new user, that user is assigned a unique identifier which corresponds with the total number of accounts that have been established.  The the program encrypts your password and stores it in a HashMap that contains all active Users of the program.  The HashMap is then serialized and stores as a .pm file on your computer.  The keyset that was used to encrypt your password is also stored on your computer (this isn't ideal.  Ideally, you would probably want to store this on a server somewhere).  When you login, the HashMap object is loaded along with the stored Keyset. The password associated with the username that is typed in is compared to the encrypted password that was stored on the computer to see if there's a match.  The passwords that you store for websites, email, etc. will be stored in a similar manner.  Everything is done locally. 

Soon, you will be able to store website and application credentials.

![Login Screen](https://github.com/tmjonker/PasswordManager/blob/master/Images/Login.PNG)              ![New user](https://github.com/tmjonker/PasswordManager/blob/master/Images/NewUser1.PNG)

## Description

Coming soon.

## Getting Started

### Dependencies

* Google Tink
* JavaFX 14
* Jasypt

### Installing

* It can be unzipped into any folder on your computer.
* Maven is the build tool.  The POM.xml is included with this repository.
* Build it with whicher IDE you choose.
  * If you're using IntelliJ, make sure to include 


### Executing program

* Build the project with your IDE of choice.


## Authors

Contributors names and contact info

* Tim Jonker
  - tmjonker1@outlook.com

## Version History

