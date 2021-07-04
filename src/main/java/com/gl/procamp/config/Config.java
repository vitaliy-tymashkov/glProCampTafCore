package com.gl.procamp.config;

import static com.gl.procamp.config.ConfigConstants.AUTHORIZATION_TYPE;
import static com.gl.procamp.config.ConfigConstants.BASE_URL;
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
import static com.gl.procamp.config.LookupOrder.DEFAULT_ONLY;
import static com.gl.procamp.config.LookupOrder.ENV_FILE_DEFAULT;
import static com.gl.procamp.config.LookupOrder.ENV_YAML_FILE_DEFAULT;
import static com.gl.procamp.config.LookupOrder.FILE_DEFAULT;
import static com.gl.procamp.config.LookupOrder.YAML_ONLY;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config extends AbstractConfig {
    private static final Logger logger = LoggerFactory.getLogger(Config.class);

    private static Config config;
    public static final String DEFAULT_TARGET = "UAT"; //Hard-coded default target to UAT

    @Setter
    private static String target = getTarget(DEFAULT_TARGET);

    @Setter
    private String targetEnvVarName;

    private Config() {
    }

    public static Config getInstance() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    public static String getTarget() {
        return getTarget(DEFAULT_TARGET);
    }

    public static String getTarget(String defaultTarget) {
        String targetEnvVarName = Config.getInstance().getTargetEnvVarName();
        try {
            Config.target = System.getenv(targetEnvVarName);
        } catch (Exception e) {
            logger.error("Failed to load {} env. Exception {}", targetEnvVarName, e.getMessage());
            target = defaultTarget;
        }
        return target;
    }

    private String getTargetEnvVarName() {
        return getValue(DEFAULT_TARGET);
    }

    public String getBaseUrl() {
        return getValue(getTarget(), BASE_URL, ENV_FILE_DEFAULT);
    }

    public String getBaseUrl(LookupOrder lookupOrder) {
        return getValue(getTarget(), BASE_URL, lookupOrder);
    }

    public String getLoginUrl() {
        return getValue(getTarget(), LOGIN_URL, YAML_ONLY);
    }

    public String getLoginUrl(LookupOrder lookupOrder) {
        return getValue(getTarget(), LOGIN_URL, lookupOrder);
    }

    public String getFilesUrlApi() {
        return getValue(getTarget(), FILES_URL_API, ENV_FILE_DEFAULT);
    }

    public String getLoginUrlApi() {
        return getValue(getTarget(), LOGIN_URL_API, FILE_DEFAULT);
    }

    public String getLoginUrlApi(LookupOrder lookupOrder) {
        return getValue(getTarget(), LOGIN_URL_API, lookupOrder);
    }

    public String getAuthType() {
        return getValue(getTarget(), AUTHORIZATION_TYPE, FILE_DEFAULT);
    }

    public String getAuthType(LookupOrder lookupOrder) {
        return getValue(getTarget(), AUTHORIZATION_TYPE, lookupOrder);
    }

    public String getExpiry() {
        return getValue(getTarget(), EXPIRY, ENV_YAML_FILE_DEFAULT);
    }

    public String getExpiry(LookupOrder lookupOrder) {
        return getValue(getTarget(), EXPIRY, lookupOrder);
    }

    public String getXToken() {
        return getValue(getTarget(), X_TOKEN, ENV_YAML_FILE_DEFAULT);
    }

    public String getLoginFrom() {
        return getValue(getTarget(), LOGIN_FROM, ENV_YAML_FILE_DEFAULT);
    }

    public String getLoginFrom(LookupOrder lookupOrder) {
        return getValue(getTarget(), LOGIN_FROM, lookupOrder);
    }

    public String getIncorrectLoginText() {
        return getValue(getTarget(), INCORRECT_LOGIN_TEXT, DEFAULT_ONLY);
    }

    public String getIncorrectLoginText(LookupOrder lookupOrder) {
        return getValue(getTarget(), INCORRECT_LOGIN_TEXT, lookupOrder);
    }

    public String getRootFolderFlag() {
        return getValue(getTarget(), ROOT_FOLDER_FLAG, ENV_YAML_FILE_DEFAULT);
    }

    public String getStatusCodeSuccess() {
        return getValue(getTarget(), STATUS_CODE_SUCCESS, ENV_YAML_FILE_DEFAULT);
    }

    public String getStatusCodeNotAuthenticated() {
        return getValue(getTarget(), STATUS_CODE_NOT_AUTHENTICATED, ENV_YAML_FILE_DEFAULT);
    }

    public String getSeleniumDriverPath() {
        return getValue(getTarget(), SELENIUM_DRIVER_PATH, ENV_YAML_FILE_DEFAULT);
    }

    public String getHeadlessMode() {
        return getValue(getTarget(), HEADLESS_MODE, ENV_YAML_FILE_DEFAULT);
    }

    public String getMainPageTitle() {
        return getValue(getTarget(), MAIN_PAGE_TITLE, ENV_YAML_FILE_DEFAULT);
    }

    public String getMainPageDefaultUrl() {
        return getValue(getTarget(), MAIN_PAGE_DEFAULT_URL, ENV_YAML_FILE_DEFAULT);
    }
}
