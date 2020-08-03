import org.example.tmjonker.PasswordManager.PasswordVault;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class TinkEncryptionDecryptionTest {
    public static void main (String[] args) {
        try {
            PasswordVault.encryptPassword("poop", "2333695");
            System.out.println(PasswordVault.decryptPassword("2333695"));
        } catch (GeneralSecurityException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
