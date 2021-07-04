package com.gl.procamp.tests.api.login;

import static com.gl.procamp.helpers.repository.TestsGroupConstants.COSMOS_ID;
import static com.gl.procamp.helpers.repository.TestsGroupConstants.LOGIN;
import static com.gl.procamp.helpers.repository.TestsGroupConstants.NEGATIVE;
import static com.gl.procamp.helpers.repository.TestsGroupConstants.SMOKE_TEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testng.AssertJUnit.assertFalse;

import com.gl.procamp.tests.AbstractBaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

public class LoginApiTest extends AbstractBaseTest {

    @Feature("API Login")
    @Story("Get login token")
    @Severity(value = SeverityLevel.BLOCKER)
    @Step("Get login token")
    @Test(groups = {SMOKE_TEST, COSMOS_ID, LOGIN})
    public void testWhenLoginWithCorrectCredentials_thenGetToken() {
        String loginToken = httpApiClient.getLoginToken();
        assertFalse("Login Token is empty", loginToken.isEmpty());
    }

    @Feature("API Login")
    @Story("Check login page not accessible with incorrect credentials")
    @Severity(value = SeverityLevel.NORMAL)
    @Step("Request login page with incorrect credentials and get expected error text")
    @Test(groups = {SMOKE_TEST, COSMOS_ID, LOGIN, NEGATIVE}, enabled = true)
    public void testWhenLoginWithIncorrectCredentials_thenGetExpectedErrorText() {
        String activeUrl = config.getBaseUrl() + config.getLoginUrlApi();
        String incorrectLoginText = httpApiClient.getIncorrectLoginText(activeUrl);

        assertThat("Login with incorrect credentials didn't return the proper message",
                incorrectLoginText, is(config.getIncorrectLoginText()));
    }
}
