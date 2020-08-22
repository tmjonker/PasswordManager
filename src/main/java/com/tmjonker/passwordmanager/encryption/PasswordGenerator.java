package com.tmjonker.passwordmanager.encryption;

import java.util.Random;

public class PasswordGenerator {

    private final String CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String generatePassword() {

        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 20; i++) {
                int randomInt = random.nextInt(CHARACTERS.length());
                String randomCharacter = CHARACTERS.substring(randomInt, randomInt+1);

                password.append(randomCharacter);
            }

        return password.toString();
    }
}
