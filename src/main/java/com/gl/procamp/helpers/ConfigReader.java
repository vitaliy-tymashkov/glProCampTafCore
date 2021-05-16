package com.gl.procamp.helpers;

import static com.gl.procamp.config.ConfigConstants.AUTH_BEARER_TOKEN;
import static com.gl.procamp.config.ConfigConstants.BASE_URL_KEY;
import static com.gl.procamp.config.ConfigConstants.LOGIN_URL;

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
        config.setBaseUrl(properties.getProperty(BASE_URL_KEY));
        config.setAuthBearerToken(properties.getProperty(AUTH_BEARER_TOKEN));
        config.setLoginUrl(properties.getProperty(LOGIN_URL));
    }
}
