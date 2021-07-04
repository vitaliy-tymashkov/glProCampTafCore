package com.gl.procamp.tests.ui;

import static com.gl.procamp.helpers.repository.TestsGroupConstants.COSMOS_ID;
import static com.gl.procamp.helpers.repository.TestsGroupConstants.UI_TEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.AssertJUnit.fail;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.gl.procamp.config.Config;
import com.gl.procamp.tests.ui.pageObjects.BasePageObject;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

@Epic("UI tests")
public class BaseUiTest {

    protected static final Config config = Config.getInstance();
    private static final String WEBDRIVER_CHROME_PROPERTY = "webdriver.chrome.driver";
    private static final String pathToChromeDriver = config.getSeleniumDriverPath();
    private static final String activeUrl = config.getBaseUrl() + config.getLoginUrl();
    private static final int TIMEOUT_IN_SECONDS = 10;
    private static final int TIMEOUT_FOR_PAGE_LOADING = 30;

    protected static WebDriver driver;

    @BeforeClass(groups = {UI_TEST, COSMOS_ID}, alwaysRun = true)
    public static void setUp() {
        System.setProperty(WEBDRIVER_CHROME_PROPERTY, pathToChromeDriver);
        driver = new ChromeDriver(getChromeOptions());
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
        driver.get(activeUrl);
    }

    @AfterClass(groups = {UI_TEST, COSMOS_ID}, alwaysRun = true)
    public static void tearDown() throws IOException {
        new BasePageObject(driver).takeScreenshot();
        driver.manage()
                .deleteAllCookies();
        driver.quit();
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("window-size=1400,800");
        options.addArguments("disable-gpu");
        options.addArguments("--disable-web-security");
        setupHeadlessModeOption(options);
        return options;
    }

    @Step("Setup Headless mode options if configured")
    private static void setupHeadlessModeOption(ChromeOptions options) {
        if (config.getHeadlessMode().equalsIgnoreCase("true") |
                config.getHeadlessMode().equalsIgnoreCase("1")) {
            options.addArguments("headless");
        }
    }

    protected void assertOnTitle() {
        String actualTitle = driver.getTitle();
        String expectedTitle = config.getMainPageTitle();
        assertThat("Title doesn't match", actualTitle, equalTo(expectedTitle));
    }

    protected void assertOnNextUrl() {
        String actualTitle = driver.getCurrentUrl();
        String expectedUrl = config.getMainPageDefaultUrl();
        assertThat("Url After First Opening doesn't match", actualTitle, equalTo(expectedUrl));
    }

    protected void waitForPageLoaded(WebDriver driver) {
        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver)
                                .executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_FOR_PAGE_LOADING);
            wait.until(expectation);
        } catch (Throwable error) {
            fail("Timeout waiting for Page Load Request to complete.");
        }
    }
}
