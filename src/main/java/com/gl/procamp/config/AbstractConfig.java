package com.gl.procamp.config;

import static com.gl.procamp.config.ConfigConstants.CONFIG_YAML_FILE_NAME;
import static com.gl.procamp.config.ConfigConstants.DEFAULT_PROPERTIES_FILE_NAME;
import static com.gl.procamp.config.ConfigConstants.ENVPROD_PROPERTIES_FILE_NAME;
import static com.gl.procamp.config.ConfigConstants.ENVUAT_PROPERTIES_FILE_NAME;
import static com.gl.procamp.helpers.ConfigReader.readConfigurationFromFile;
import static org.testng.AssertJUnit.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

public class AbstractConfig {
    private static final Logger logger = LoggerFactory.getLogger(AbstractConfig.class);
    private static Config config = Config.getInstance();

    @Setter
    protected String authType; //Accessed via Java reflection
    @Setter
    protected String expiry; //Accessed via Java reflection
    @Setter
    protected String loginFrom; //Accessed via Java reflection
    @Setter
    protected String baseUrl; //Accessed via Java reflection
    @Setter
    protected String loginUrl; //Accessed via Java reflection
    @Setter
    protected String loginUrlApi; //Accessed via Java reflection

    protected String getValue(String field) {
        return getValueFromEnvironmentVariables(field);
    }

        protected String getValue(String envName, String field, LookupOrder lookupOrder) {
        Env env = Env.valueOf(envName.toUpperCase());


        String value;
        for (LookupEntity lookupEntity : lookupOrder.getLookupOrder()) {
            switch (lookupEntity) {
                case ENV: {
                    value = getValueFromEnvironmentVariables(field);
                    break;
                }
                case FILE: {
                    value = getValueFromFile(env, field);
                    break;
                }
                case YAML: {
                    value = getValueFromYamlFile(env, field);
                    break;
                }
                default: {
                    value = getValueFromDef(env, field);
                    break;
                }
            }

            if (value != null) {
                return value;
            }
        }
        logger.error("No value found for {} in {}", field, env);
        fail("Cannot find value {} in {}");
        return null;
    }

    private String getValueFromYamlFile(Env env, String field) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(CONFIG_YAML_FILE_NAME);
        } catch (FileNotFoundException e) {
            String errorLog = String.format("Not parsed {} in {}", field, env);
            logger.error(errorLog);
            fail(errorLog);
        }
        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(inputStream);
        return data.get(field).toString();
    }

    private String getValueFromDef(Env env, String field) {
        readConfigurationFromFile(DEFAULT_PROPERTIES_FILE_NAME);
        return getValueFromConfig(env, field);
    }

    private String getValueFromEnvironmentVariables(String field) {
        String value = null;
        try {
            value = System.getenv(field);
        } catch (Exception e) {
            logger.error("No value found for {}", field);
        }
        return value;
    }

    private String getValueFromFile(Env env, String field) {
        switch (env) {
            case PROD: {
                readConfigurationFromFile(ENVPROD_PROPERTIES_FILE_NAME);
            }
            case UAT: {
                readConfigurationFromFile(ENVUAT_PROPERTIES_FILE_NAME);
            }
        }
        return getValueFromConfig(env, field);
    }

    private String getValueFromConfig(Env env, String field) {
        try {
            Field declaredField = getClass().getSuperclass().getDeclaredField(field);
            declaredField.setAccessible(true);
            return (String) declaredField.get(Config.getInstance());
        } catch (NoSuchFieldException e) {
            logger.error("No such field {}", e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error("Illegal access {}", e.getMessage());
        }
        logger.error("No value found for {} in {}", field, env);
        return null;
    }
}
