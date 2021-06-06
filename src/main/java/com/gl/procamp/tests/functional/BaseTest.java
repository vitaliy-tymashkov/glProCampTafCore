package com.gl.procamp.tests.functional;

import static com.gl.procamp.tests.repository.TestsGroupConstants.COSMOS_ID;
import static com.gl.procamp.tests.repository.TestsGroupConstants.SMOKE_TEST;

import java.util.HashMap;
import java.util.Map;

import com.gl.procamp.apiClients.HttpApiClient;
import com.gl.procamp.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    protected  static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
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
}
