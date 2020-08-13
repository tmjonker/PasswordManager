package com.tmjonker.passwordmanager.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Generates the folders that are used for the resources that will be used for local disk file storage.
 *
 * @author Tim Jonker
 *
 */
public class FileStructureCreator {
    /**
     * @param path      the path that the user is running PasswordManager from on their computer.
     */
    String path = System.getProperty("user.dir");

    /**
     * Creates a new instance of FileStructureCreator, creates folder for keyset, user, and credential
     * storage.
     */
    public FileStructureCreator() {

        hideFolder(makeDirectory("keysets"));
        hideFolder(makeDirectory("users"));
        hideFolder(makeDirectory("credentials"));

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path + "\\users\\user.pm"));
            fileOutputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Hides folder (Microsoft Windows) on local disk storage.
     * @param folder        the folder that is to be hidden.
     */
    private void hideFolder(File folder) {

        Path folderPath = Paths.get(folder.toURI());
        try {
            Files.setAttribute(folderPath, "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Creates a directory on local disk for storage of program resources.
     * @param folderName        the folder that is to be created
     * @return      returns a File instance of the folder that was created.
     */
    private File makeDirectory(String folderName) {

        File folder = new File(path + "\\" + folderName);
        folder.mkdir();

        return folder;
    }
}
