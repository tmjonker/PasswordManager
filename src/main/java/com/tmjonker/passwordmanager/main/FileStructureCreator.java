package com.tmjonker.passwordmanager.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileStructureCreator {

    String path = System.getProperty("user.dir");

    public FileStructureCreator() {

        makeDirectory("users");
    }

    private File makeDirectory(String folderName) {

        File folder = new File(path + "/" + folderName);
        folder.mkdir();

        return folder;
    }
}
