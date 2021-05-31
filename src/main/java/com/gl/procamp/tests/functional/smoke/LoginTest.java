package com.gl.procamp.tests.functional.smoke;

import static com.gl.procamp.tests.repository.TestsGroupConstants.COSMOS_ID;
import static com.gl.procamp.tests.repository.TestsGroupConstants.SMOKE_TEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.fail;
import static org.testng.AssertJUnit.assertFalse;

import java.io.IOException;

import com.gl.procamp.apiClients.HttpApiClient;
import com.gl.procamp.config.Config;
import com.gl.procamp.config.LookupOrder;
import com.gl.procamp.tests.repository.TestsConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Set up ENV variables
 * KS_PASS=**************;KS_PATH=keystore.ks;target=UAT
 * */
public class LoginTest extends AbstractLoginTest {
    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);

    HttpApiClient httpApiClient;
    Config config = Config.getInstance();

    @BeforeClass(groups = {SMOKE_TEST, COSMOS_ID})
    public void setUp() {
        httpApiClient = new HttpApiClient();
    }

    @AfterClass(groups = {SMOKE_TEST, COSMOS_ID})
    public void tearDown() {
        httpApiClient.disconnect();
    }

    @Test(groups = {SMOKE_TEST, COSMOS_ID})
    public void testLoginStatusCode() {
        try {
            String activeUrl = config.getBaseUrl(LookupOrder.ENV_FILE_DEFAULT) + config.getLoginUrl();
            int actualStatusCode = httpApiClient.getStatusCode(activeUrl);

            assertThat("Login page status code is not success", actualStatusCode, is(TestsConstants.EXPECTED_STATUS_CODE_SUCCESS));
        } catch (IOException e) {
            fail("Failed with IOException: " + e.getMessage());
        }
    }

    @Test(groups = {SMOKE_TEST, COSMOS_ID})
    public void testWhenLoginWithCorrectCredentials_thenGetToken() {
        try {
            String activeUrl = config.getBaseUrl() + config.getLoginUrlApi();
            String loginToken = httpApiClient.getLoginToken(activeUrl);
            logger.debug("Login token = " + loginToken);

            assertFalse("Login Token is empty", loginToken.isEmpty());
        } catch (IOException e) {
            logger.error("Failed with IOException {}", e.getMessage());
            fail("Failed with IOException: " + e.getMessage());
        }
    }

    @Test(groups = {COSMOS_ID})
    public void testLoginPageTitle() {
        try {
            String activeUrl = config.getBaseUrl() + config.getLoginUrl();
            String actualPage = httpApiClient.getPage(activeUrl);
            String actualLoginPageTitle = getActualLoginPageTitle(actualPage);

            assertThat("Login page status code is not success", actualLoginPageTitle, is(TestsConstants.EXPECTED_LOGIN_PAGE_TITLE));
        } catch (IOException e) {
            logger.error("Failed with IOException {}", e.getMessage());
            fail("Failed with IOException: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            logger.error("Failed to find title {}", e.getMessage());
            fail("Failed to find title: " + e.getMessage());
        }
    }
}
