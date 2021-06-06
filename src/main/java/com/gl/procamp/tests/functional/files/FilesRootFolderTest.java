package com.gl.procamp.tests.functional.files;

import static com.gl.procamp.tests.repository.TestsGroupConstants.COSMOS_ID;
import static com.gl.procamp.tests.repository.TestsGroupConstants.FILES_ROOT_FOLDER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.util.HashMap;
import java.util.Map;

import com.gl.procamp.tests.functional.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FilesRootFolderTest extends BaseTest {

    private String loginToken;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        super.setUp();
        loginToken = httpApiClient.getLoginToken();
    }

    @Test(groups = {COSMOS_ID, FILES_ROOT_FOLDER})
    public void testWhenGetFilesRootFolder_thenGetRootContent() {

        String activeUrl = config.getBaseUrl() + config.getFilesUrlApi();
        Map<String, String> headers = new HashMap<>();
        headers.put(config.getXToken(), loginToken);
        String actualPage = httpApiClient.getPage(activeUrl, headers);

        //TODO Introduce business logic assertions
        assertThat("Response doesn't contain ROOT object",
                actualPage, containsString("\"name\": \"ROOT\""));
    }
}
