package com.gl.procamp.helpers;

import static com.gl.procamp.config.ConfigConstants.AUTH_TYPE;
import static com.gl.procamp.config.ConfigConstants.BASE_URL_KEY;
import static com.gl.procamp.config.ConfigConstants.EXPIRY;
import static com.gl.procamp.config.ConfigConstants.FILES_URL_API;
import static com.gl.procamp.config.ConfigConstants.HEADLESS_MODE;
import static com.gl.procamp.config.ConfigConstants.INCORRECT_LOGIN_TEXT;
import static com.gl.procamp.config.ConfigConstants.LOGIN_FROM;
import static com.gl.procamp.config.ConfigConstants.LOGIN_URL;
import static com.gl.procamp.config.ConfigConstants.LOGIN_URL_API;
import static com.gl.procamp.config.ConfigConstants.MAIN_PAGE_DEFAULT_URL;
import static com.gl.procamp.config.ConfigConstants.MAIN_PAGE_TITLE;
import static com.gl.procamp.config.ConfigConstants.ROOT_FOLDER_FLAG;
import static com.gl.procamp.config.ConfigConstants.SELENIUM_DRIVER_PATH;
import static com.gl.procamp.config.ConfigConstants.STATUS_CODE_NOT_AUTHENTICATED;
import static com.gl.procamp.config.ConfigConstants.STATUS_CODE_SUCCESS;
import static com.gl.procamp.config.ConfigConstants.X_TOKEN;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.gl.procamp.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigReader {
    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);

    public static void readConfigurationFromFile(String propertiesFileName) {
        FileInputStream fileInputStream;
        Properties properties = new Properties();
        try {
            fileInputStream = new FileInputStream(propertiesFileName);
            properties.load(fileInputStream);
            setConfigProperties(properties, Config.getInstance());
        } catch (IOException e) {
            logger.error("IOException while reading {}", e.getMessage());
        }
        logger.debug("Configs updated.");
    }

    private static void setConfigProperties(Properties properties, Config config) {
        config.setAuthType(properties.getProperty(AUTH_TYPE));
        config.setExpiry(properties.getProperty(EXPIRY));
        config.setLoginFrom(properties.getProperty(LOGIN_FROM));
        config.setBaseUrl(properties.getProperty(BASE_URL_KEY));
        config.setLoginUrl(properties.getProperty(LOGIN_URL));
        config.setLoginUrlApi(properties.getProperty(LOGIN_URL_API));
        config.setXToken(properties.getProperty(X_TOKEN));
        config.setFilesUrlApi(properties.getProperty(FILES_URL_API));
        config.setIncorrectLoginText(properties.getProperty(INCORRECT_LOGIN_TEXT));
        config.setRootFolderFlag(properties.getProperty(ROOT_FOLDER_FLAG));
        config.setStatusCodeSuccess(properties.getProperty(STATUS_CODE_SUCCESS));
        config.setStatusCodeNotAuthenticated(properties.getProperty(STATUS_CODE_NOT_AUTHENTICATED));
        config.setSeleniumDriverPath(properties.getProperty(SELENIUM_DRIVER_PATH));
        config.setHeadlessMode(properties.getProperty(HEADLESS_MODE));
        config.setMainPageTitle(properties.getProperty(MAIN_PAGE_TITLE));
        config.setMainPageDefaultUrl(properties.getProperty(MAIN_PAGE_DEFAULT_URL));
    }
}
