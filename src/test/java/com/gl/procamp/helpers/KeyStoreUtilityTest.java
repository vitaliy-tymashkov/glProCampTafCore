package com.gl.procamp.helpers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

import org.assertj.core.api.SoftAssertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class KeyStoreUtilityTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeyStoreUtilityTest.class.getName());

    //Secrets hardcoded only for educational purposes
    private static final String KS_PATH = "keystore_for_unit_test.ks";
    private static final char[] KS_PASS = "testtesttest1".toCharArray();
    private static final char[] EXPECTED_USER_NAME = "test@test.com".toCharArray();
    private static final char[] EXPECTED_USER_PASSWORD = "testtesttesttest".toCharArray();
    private static final String USER_KS_ALIAS = "user";
    private static final String PASSWORD_KS_ALIAS = "password";

    @BeforeMethod
    public void setUp() {
        File ksFile = new File(KS_PATH);
        boolean ksFileExists = ksFile.exists();

        assertThat("KS file doesn't exist", ksFileExists, equalTo(true));
    }

    @Test
    public void test_whenGetSecretFromKeystoreForUnitTests_thenGetExpectedSecrets() {
        try {
            char[] actual_user_name = KeyStoreUtility.getSecretFromKeystore(USER_KS_ALIAS, KS_PATH, KS_PASS);
            char[] actual_user_pass = KeyStoreUtility.getSecretFromKeystore(PASSWORD_KS_ALIAS, KS_PATH, KS_PASS);

            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(actual_user_name).as("User name doesn't match").isEqualTo(EXPECTED_USER_NAME);
            softly.assertThat(actual_user_pass).as("User password doesn't match").isEqualTo(EXPECTED_USER_PASSWORD);
            softly.assertAll();
        } catch (KeyStoreException | CertificateException | IOException |
                NoSuchAlgorithmException | InvalidKeySpecException | UnrecoverableEntryException e) {
            LOGGER.error("Failed to get secrets from {} in unit test because of {}", KS_PATH, e.getMessage());
        }
    }
}