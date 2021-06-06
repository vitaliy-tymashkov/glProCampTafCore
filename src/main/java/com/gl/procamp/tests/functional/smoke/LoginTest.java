package com.gl.procamp.tests.functional.smoke;

import static com.gl.procamp.tests.repository.TestsConstants.LOGIN_PAGE_MATCHER;
import static com.gl.procamp.tests.repository.TestsGroupConstants.COSMOS_ID;
import static com.gl.procamp.tests.repository.TestsGroupConstants.LOGIN;
import static com.gl.procamp.tests.repository.TestsGroupConstants.NEGATIVE;
import static com.gl.procamp.tests.repository.TestsGroupConstants.SMOKE_TEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testng.AssertJUnit.assertFalse;

import com.gl.procamp.config.LookupOrder;
import com.gl.procamp.tests.repository.TestsConstants;
import org.testng.annotations.Test;

/**
 * Set up ENV variables
 * KS_PASS=**************;KS_PATH=keystore.ks;target=UAT
 */
public class LoginTest extends AbstractLoginTest {

    @Test(groups = {SMOKE_TEST, COSMOS_ID})
    public void testLoginStatusCode() {
        String activeUrl = config.getBaseUrl(LookupOrder.ENV_FILE_DEFAULT) + config.getLoginUrl();
        int actualStatusCode = httpApiClient.getStatusCode(activeUrl);

        assertThat("Login page status code is not success",
                actualStatusCode, is(TestsConstants.EXPECTED_STATUS_CODE_SUCCESS));
    }

    @Test(groups = {SMOKE_TEST, COSMOS_ID, LOGIN})
    public void testWhenLoginWithCorrectCredentials_thenGetToken() {
        String loginToken = httpApiClient.getLoginToken();
        assertFalse("Login Token is empty", loginToken.isEmpty());
    }

    @Test(groups = {SMOKE_TEST, COSMOS_ID, LOGIN, NEGATIVE}, enabled = true)
    public void testWhenLoginWithIncorrectCredentials_thenGetError() {
        String activeUrl = config.getBaseUrl() + config.getLoginUrlApi();
        String incorrectLoginText = httpApiClient.getIncorrectLoginText(activeUrl);

        assertThat("Login with incorrect credentials didn't return the proper message",
                incorrectLoginText, is(config.getIncorrectLoginText()));
    }

    @Test(groups = {COSMOS_ID})
    public void testLoginPageTitle() {
        String activeUrl = config.getBaseUrl() + config.getLoginUrl();
        String actualPage = httpApiClient.getPage(activeUrl);
        String actualLoginPageTitle = parsePageWithMatcher(actualPage, LOGIN_PAGE_MATCHER);

        assertThat("Login page title is not correct",
                actualLoginPageTitle, is(TestsConstants.EXPECTED_LOGIN_PAGE_TITLE));
    }
}
