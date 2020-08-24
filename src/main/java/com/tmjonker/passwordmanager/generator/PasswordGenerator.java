package com.tmjonker.passwordmanager.generator;

import java.util.Random;

public class PasswordGenerator {

    // 0-9 are included twice to increase likelihood of a numbers being included in generated password.
    private static final String CHARACTERS =
            "0123456789!@&$abcdefghijklmnopqrstuvwxyz!@&$ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@&$";

    public static String generatePassword() {

        Random random = new Random();
        StringBuilder password = new StringBuilder();
        String randomCharacter;

        for (int i = 0; i < 20; i++) {
                int randomInt = random.nextInt(CHARACTERS.length());
                randomCharacter = CHARACTERS.substring(randomInt, randomInt+1);

                password.append(randomCharacter);
            }

        return password.toString();
    }
}
