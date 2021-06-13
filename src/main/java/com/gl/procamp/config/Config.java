package com.gl.procamp.config;

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
        return getValue("targetEnvVarName");
    }

    public String getBaseUrl() {
        return getValue(getTarget(), "baseUrl", ENV_FILE_DEFAULT);
    }

    public String getBaseUrl(LookupOrder lookupOrder) {
        return getValue(getTarget(), "baseUrl", lookupOrder);
    }

    public String getLoginUrl() {
        return getValue(getTarget(), "loginUrl", YAML_ONLY);
    }

    public String getFilesUrlApi() {
        return getValue(getTarget(), "filesUrlApi", ENV_FILE_DEFAULT);
    }

    public String getLoginUrl(LookupOrder lookupOrder) {
        return getValue(getTarget(), "loginUrl", lookupOrder);
    }

    public String getLoginUrlApi() {
        return getValue(getTarget(), "loginUrlApi", FILE_DEFAULT);
    }

    public String getLoginUrlApi(LookupOrder lookupOrder) {
        return getValue(getTarget(), "loginUrlApi", lookupOrder);
    }

    public String getAuthType() {
        return getValue(getTarget(), "authType", FILE_DEFAULT);
    }

    public String getAuthType(LookupOrder lookupOrder) {
        return getValue(getTarget(), "authType", lookupOrder);
    }

    public String getExpiry() {
        return getValue(getTarget(), "expiry", ENV_YAML_FILE_DEFAULT);
    }

    public String getXToken() {
        return getValue(getTarget(), "xToken", ENV_YAML_FILE_DEFAULT);
    }

    public String getExpiry(LookupOrder lookupOrder) {
        return getValue(getTarget(), "expiry", lookupOrder);
    }

    public String getLoginFrom() {
        return getValue(getTarget(), "loginFrom", ENV_YAML_FILE_DEFAULT);
    }

    public String getLoginFrom(LookupOrder lookupOrder) {
        return getValue(getTarget(), "loginFrom", lookupOrder);
    }

    public String getIncorrectLoginText() {
        return getValue(getTarget(), "incorrectLoginText", DEFAULT_ONLY);
    }

    public String getIncorrectLoginText(LookupOrder lookupOrder) {
        return getValue(getTarget(), "incorrectLoginText", lookupOrder);
    }

    public String getRootFolderFlag() {
        return getValue(getTarget(), "rootFolderFlag", ENV_YAML_FILE_DEFAULT);
    }

    public String getStatusCodeSuccess() {
        return getValue(getTarget(), "statusCodeSuccess", ENV_YAML_FILE_DEFAULT);
    }

    public String getStatusCodeNotAuthenticated() {
        return getValue(getTarget(), "statusCodeNotAuthenticated", ENV_YAML_FILE_DEFAULT);
    }

    public String getSeleniumDriverPath() {
        return getValue(getTarget(), "seleniumDriverPath", ENV_YAML_FILE_DEFAULT);
    }

    public String getHeadlessMode() {
        return getValue(getTarget(), "headlessMode", ENV_YAML_FILE_DEFAULT);
    }

}
