package com.tmjonker.passwordmanager.main;

import com.tmjonker.passwordmanager.encryption.PasswordGenerator;

public class Main {

    public static void main(String[] args) {

        FileStructureCreator.generateFileFolders();
        Bridge.main(args);
    }
}
