package org.example.tmjonker.PasswordManager;

import java.io.File;

public class FileStructureCreator {

    public FileStructureCreator() {

        new File("keysets").mkdir();

        new File("users").mkdir();

        new File("credentials").mkdir();
    }
}
