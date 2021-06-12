package com.gl.procamp.helpers;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Set up ENV variables
 * KS_PASS=*************;KS_PATH=keystore.ks;user=***********@**********.com;password=***************
 * The last two variables required only for creating a new keystore
 *
 * To save some secrets to keystore use safeMakeNewKeystores()
 * or makeNewKeystoreEntries(DATA_KEYSTORE_KS, ksPass);
 *
 * To get some secrets from keystore use safeGetSecretFromKeystore()
 * getSecretFromKeystore(USER_ALIAS, DATA_KEYSTORE_KS, ksPass);
 *
 * */
public class KeyStoreUtility {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigReader.class);

    private static final String DATA_KEYSTORE_KS = System.getenv("KS_PATH");
    private static final String USER_KS_ALIAS = "user";
    private static final String PASSWORD_KS_ALIAS = "password";
    private static final String JCEKS = "JCEKS";
    private static final String PBE = "PBE";
    private static final char[] ksPass = System.getenv("KS_PASS").toCharArray();
    private static final String USER_ENV = "user";
    private static final String PASSWORD_ENV = "password";

    private KeyStoreUtility() {
        throw new UnsupportedOperationException("Utility class is not intended to be instantiated.");
    }

    public static void main(String[] args) {
        System.out.println("KS Utils - stub message");
        //Example of usage
//        safeMakeNewKeystore();
//        safeGetSecretFromKeystore();
    }

    private static void safeMakeNewKeystore() {
        try {
            makeNewKeystoreEntries(DATA_KEYSTORE_KS, ksPass);
        } catch (Exception e) {
            System.out.println("Exception " + e.getMessage());
        }
    }

    private static void safeGetSecretFromKeystore() {
        try {
            System.out.println(getSecretFromKeystore(USER_KS_ALIAS, DATA_KEYSTORE_KS, ksPass));
            System.out.println(getSecretFromKeystore(PASSWORD_KS_ALIAS, DATA_KEYSTORE_KS, ksPass));
        } catch (Exception e) {
            System.out.println("Exception " + e.getMessage());
        }
    }

    private static void makeNewKeystoreEntries(String keyStoreLocation,
                                               char[] keyStorePassword) throws
            NoSuchAlgorithmException, InvalidKeySpecException, KeyStoreException, CertificateException, IOException {
        char[] user = System.getenv(USER_ENV).toCharArray();
        char[] password = System.getenv(PASSWORD_ENV).toCharArray();

        SecretKeyFactory factory = SecretKeyFactory.getInstance(PBE);
        SecretKey generatedSecret1 =
                factory.generateSecret(new PBEKeySpec(user));
        SecretKey generatedSecret2 =
                factory.generateSecret(new PBEKeySpec(password));

        KeyStore ks = KeyStore.getInstance(JCEKS);
        ks.load(null, keyStorePassword);

        KeyStore.PasswordProtection keyStorePP =
                new KeyStore.PasswordProtection(keyStorePassword);

        ks.setEntry(
                USER_KS_ALIAS,
                new KeyStore.SecretKeyEntry(generatedSecret1),
                keyStorePP);
        ks.setEntry(
                PASSWORD_KS_ALIAS,
                new KeyStore.SecretKeyEntry(generatedSecret2),
                keyStorePP);

        try (FileOutputStream fos = new FileOutputStream(keyStoreLocation)) {
            ks.store(fos, keyStorePassword);
        }
    }

    public static char[] getSecretFromKeystore(String entry, String keystoreLocation, char[] keyStorePassword) throws
            KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, UnrecoverableEntryException {

        KeyStore ks = KeyStore.getInstance(JCEKS);
        ks.load(null, keyStorePassword);
        KeyStore.PasswordProtection keyStorePP = new KeyStore.PasswordProtection(keyStorePassword);

        FileInputStream fIn = new FileInputStream(keystoreLocation);

        ks.load(fIn, keyStorePassword);

        SecretKeyFactory factory = SecretKeyFactory.getInstance(PBE);

        KeyStore.SecretKeyEntry ske = (KeyStore.SecretKeyEntry) ks.getEntry(entry, keyStorePP);

        PBEKeySpec keySpec = (PBEKeySpec) factory.getKeySpec(
                ske.getSecretKey(),
                PBEKeySpec.class);

        return keySpec.getPassword();
    }

    @Step("Get secret for {userAlias} from keystore")
    public static char[] getSecret(String userAlias) {
        try {
            return getSecretFromKeystore(userAlias, DATA_KEYSTORE_KS, ksPass);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }
}
