package com.gl.procamp.tests.functional.smoke;

import static com.gl.procamp.config.ConfigConstants.CONFIG_PROPERTIES_FILE_NAME;
import static com.gl.procamp.helpers.ConfigReader.readConfigurationFromFile;
import static com.gl.procamp.tests.repository.TestsGroupConstants.COSMOS_ID;
import static com.gl.procamp.tests.repository.TestsGroupConstants.SMOKE_TEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.fail;
import static org.testng.AssertJUnit.assertFalse;

import java.io.IOException;

import com.gl.procamp.apiClients.HttpApiClient;
import com.gl.procamp.config.Config;
import com.gl.procamp.tests.repository.TestsConstants;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Set up ENV variables
 * KS_PASS=**************;KS_PATH=keystore.ks
 * */
public class LoginTest extends AbstractLoginTest {
    HttpApiClient httpApiClient;

    @BeforeClass(groups = {SMOKE_TEST, COSMOS_ID})
    public void setUp() {
        readConfigurationFromFile(CONFIG_PROPERTIES_FILE_NAME);
        LOGIN_URL = Config.getInstance().getBaseUrl() + Config.getInstance().getLoginUrl();
        LOGIN_URL_API = Config.getInstance().getBaseUrl() + Config.getInstance().getLoginUrlApi();
        httpApiClient = new HttpApiClient();
    }

    @AfterClass(groups = {SMOKE_TEST, COSMOS_ID})
    public void tearDown() {
        httpApiClient.disconnect();
    }

    @Test(groups = {SMOKE_TEST, COSMOS_ID})
    public void testLoginStatusCode() {
        try {
            int actualStatusCode = httpApiClient.getStatusCode(LOGIN_URL);

            assertThat("Login page status code is not success", actualStatusCode, is(TestsConstants.EXPECTED_STATUS_CODE_SUCCESS));
        } catch (IOException e) {
            fail("Failed with IOException: " + e.getMessage());
        }
    }

    @Test(groups = {SMOKE_TEST, COSMOS_ID})
    public void testWhenLoginWithCorrectCredentials_thenGetToken() {
        try {
            String loginToken = httpApiClient.getLoginToken(LOGIN_URL_API);
            System.out.println("Login token = " + loginToken);

            assertFalse("Login Token is empty", loginToken.isEmpty());
        } catch (IOException e) {
            fail("Failed with IOException: " + e.getMessage());
        }
    }

    @Test(groups = {COSMOS_ID})
    public void testLoginPageTitle() {
        try {
            String actualPage = httpApiClient.getPage(LOGIN_URL);
            String actualLoginPageTitle = getActualLoginPageTitle(actualPage);

            assertThat("Login page status code is not success", actualLoginPageTitle, is(TestsConstants.EXPECTED_LOGIN_PAGE_TITLE));
        } catch (IOException e) {
            fail("Failed with IOException: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            fail("Failed to find title: " + e.getMessage());
        }
    }
}
