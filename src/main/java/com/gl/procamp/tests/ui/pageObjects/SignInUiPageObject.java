package com.gl.procamp.tests.ui.pageObjects;

import com.gl.procamp.tests.BasePageObject;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignInUiPageObject extends BasePageObject {

    @FindBy(xpath = "//input[@id='email']")
    private WebElement loginInput;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@id='signInButton']")
    private WebElement signInButton;

    @FindBy(xpath = "//span[contains(text(), 'Ok')]/parent::button")
    private WebElement acceptAnnouncementButton;

    public SignInUiPageObject(WebDriver driver) {
        super(driver);
    }

    @Step("Accept Announcement Message")
    public void acceptAnnouncement() {
        clickButton(acceptAnnouncementButton);
    }

//TODO Investigate how to hide sensitive information - use "@Secret" annotation (failed)
//    @Step("Enter credentials for {login} and click SignIn button")
    public void signInWithLoginPage(char[] login, char[] password) {
        sendKeys(loginInput, login);
        sendKeys(passwordInput, password);
        clickButton(signInButton);
    }
}
