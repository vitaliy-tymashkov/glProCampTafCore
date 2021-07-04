package com.gl.procamp.tests.ui;

import static com.gl.procamp.helpers.repository.TestsGroupConstants.COSMOS_ID;
import static com.gl.procamp.helpers.repository.TestsGroupConstants.LOGIN;
import static com.gl.procamp.helpers.repository.TestsGroupConstants.UI_TEST;

import com.gl.procamp.helpers.KeyStoreUtility;
import com.gl.procamp.tests.ui.pageObjects.SignInUiPageObject;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

public class SignInUiPageTestTest extends BaseUiTest {
    private static final String USER_KS_ALIAS = "user";
    private static final String PASSWORD_KS_ALIAS = "password";

    @Feature("UI Login")
    @Story("Login on web page")
    @Severity(value = SeverityLevel.BLOCKER)
    @Test(groups = {UI_TEST, COSMOS_ID, LOGIN})
    public void test_whenSignInWithValidUser_thenGetMainPage() {

        SignInUiPageObject signInPage = new SignInUiPageObject(BaseUiTest.driver);
        signInPage.acceptAnnouncement();
        signInPage.signInWithLoginPage(KeyStoreUtility.getSecret(USER_KS_ALIAS), KeyStoreUtility.getSecret(PASSWORD_KS_ALIAS));

        waitForPageLoaded(driver);
        assertOnTitle();
        assertOnNextUrl();
    }
}
