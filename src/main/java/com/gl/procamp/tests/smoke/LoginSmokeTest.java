package com.gl.procamp.tests.smoke;

import static com.gl.procamp.helpers.repository.TestsConstants.LOGIN_PAGE_MATCHER;
import static com.gl.procamp.helpers.repository.TestsGroupConstants.COSMOS_ID;
import static com.gl.procamp.helpers.repository.TestsGroupConstants.SMOKE_TEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import com.gl.procamp.config.LookupOrder;
import com.gl.procamp.helpers.repository.TestsConstants;
import com.gl.procamp.tests.AbstractBaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

public class LoginSmokeTest extends AbstractBaseTest {

    @Feature("Login Web page is accessible")
    @Story("Check login Web page status code")
    @Step("Check login Web page status code")
    @Severity(value = SeverityLevel.MINOR)
    @Test(groups = {SMOKE_TEST, COSMOS_ID})
    public void testLoginStatusCode() {
        String activeUrl = config.getBaseUrl(LookupOrder.ENV_FILE_DEFAULT) + config.getLoginUrl();
        int actualStatusCode = httpApiClient.getStatusCode(activeUrl);

        assertThat("Login page status code is not success",
                String.valueOf(actualStatusCode), equalTo(config.getStatusCodeSuccess()));
    }

    @Feature("Login Web page title")
    @Story("Check login Web page title")
    @Severity(value = SeverityLevel.MINOR)
    @Step("Get Login web page title")
    @Test(groups = {COSMOS_ID})
    public void testLoginPageTitle() {
        String activeUrl = config.getBaseUrl() + config.getLoginUrl();
        String actualPage = httpApiClient.getPage(activeUrl);
        String actualLoginPageTitle = parsePageWithMatcher(actualPage, LOGIN_PAGE_MATCHER);

        assertThat("Login page title is not correct",
                actualLoginPageTitle, is(TestsConstants.EXPECTED_LOGIN_PAGE_TITLE));
    }
}
