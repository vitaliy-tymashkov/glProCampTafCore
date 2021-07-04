package com.gl.procamp.tests.ui.pageObjects;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasePageObject {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasePageObject.class);
    private static final String SCREENSHOTS_FILE_PATH = "screenshots";
    private static final String SCREENSHOTS_FILE_PATH_TEMPLATE = SCREENSHOTS_FILE_PATH + "/%s.png";
    private static final String TIMESTAMP_PATTERN = "YYYY_MMM_dd_HH_mm_ss";
    private static final int TIMEOUT_IN_SECONDS = 10;

    protected WebDriver driver;

    public BasePageObject(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected void clickButton(WebElement button) {
        waitUntilElementIsClickable(button);
        button.click();
    }

    protected void sendKeys(WebElement webElement, char[] text) {
        waitUntilElementIsVisible(webElement);
        webElement.click();
        webElement.sendKeys(new String(text));
    }

    public void waitUntilElementIsClickable(WebElement webElement) {
        try {
            new WebDriverWait(driver, TIMEOUT_IN_SECONDS)
                    .until(elementToBeClickable(webElement));
        } catch (StaleElementReferenceException e) {
            LOGGER.error("Stale element reference Exception {}", e.getMessage());
        }
    }

    public void waitUntilElementIsVisible(WebElement webElement) {
        try {
            new WebDriverWait(driver, TIMEOUT_IN_SECONDS)
                    .until(visibilityOf(webElement));
        } catch (StaleElementReferenceException e) {
            new WebDriverWait(driver, TIMEOUT_IN_SECONDS)
                    .until(visibilityOf(webElement));
        }
    }

    @Step("Take and store screenshot")
    public void takeScreenshot() throws IOException {
        File destinationFile = prepareFileToStoreScreenshot();
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        clearFolderAndStoreScreenshot(screenshotFile, destinationFile);
    }

    private File prepareFileToStoreScreenshot() {
        String generatedString = new SimpleDateFormat(TIMESTAMP_PATTERN)
                .format(new Date(System.currentTimeMillis()));
        File destinationFile = new File(String.format(SCREENSHOTS_FILE_PATH_TEMPLATE, generatedString));
        LOGGER.info("Screenshot stored to {}", destinationFile.getAbsoluteFile());
        return destinationFile;
    }

    private void clearFolderAndStoreScreenshot(File screenshotFile, File destinationFile) throws IOException {
        FileUtils.cleanDirectory(new File(SCREENSHOTS_FILE_PATH));
        FileUtils.copyFile(screenshotFile, destinationFile);
    }
}
