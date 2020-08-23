package com.tmjonker.passwordmanager.generator;

import java.util.Random;

public class PasswordGenerator {

    private static final String CHARACTERS =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generatePassword() {

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
