package com.gl.procamp.tests;

import static com.gl.procamp.helpers.repository.TestsGroupConstants.COSMOS_ID;
import static com.gl.procamp.helpers.repository.TestsGroupConstants.SMOKE_TEST;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gl.procamp.apiClients.HttpApiClient;
import com.gl.procamp.config.Config;
import com.gl.procamp.helpers.validators.AbstractJsonSchemaValidator;
import io.qameta.allure.Epic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * Set up ENV variables
 * KS_PASS=**************;KS_PATH=keystore.ks;target=UAT
 */
@Epic("Login")
public abstract class AbstractBaseTest extends AbstractJsonSchemaValidator {
    protected  static final Logger logger = LoggerFactory.getLogger(AbstractBaseTest.class);
    protected Config config = Config.getInstance();
    protected HttpApiClient httpApiClient;

    @BeforeClass(groups = {SMOKE_TEST, COSMOS_ID}, alwaysRun = true)
    protected void setUp() {
        httpApiClient = new HttpApiClient();
    }

    @AfterClass(groups = {SMOKE_TEST, COSMOS_ID}, alwaysRun = true)
    protected void tearDown() {
        httpApiClient.disconnect();
    }

    protected Map<String, String> getHeadersXTokenMap(String loginToken) {
        Map<String, String> headers = new HashMap<>();
        headers.put(config.getXToken(), loginToken);
        return headers;
    }

    protected String parsePageWithMatcher(String actualPage, String matcherString) {
        String actualLoginPageTitle = null;
        Pattern pattern = Pattern.compile(matcherString);
        Matcher matcher = pattern.matcher(actualPage);
        if (matcher.find()) {
            actualLoginPageTitle = matcher.group(1);
        }
        return actualLoginPageTitle;
    }
}
