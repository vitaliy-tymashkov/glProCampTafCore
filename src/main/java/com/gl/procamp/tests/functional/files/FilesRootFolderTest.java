package com.gl.procamp.tests.functional.files;

import static com.gl.procamp.tests.repository.TestsConstants.ID_FOLDER_MATCHER;
import static com.gl.procamp.tests.repository.TestsGroupConstants.COSMOS_ID;
import static com.gl.procamp.tests.repository.TestsGroupConstants.FILES_ROOT_FOLDER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import com.gl.procamp.tests.functional.smoke.AbstractLoginTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FilesRootFolderTest extends AbstractLoginTest {

    private String loginToken;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        super.setUp();
        loginToken = httpApiClient.getLoginToken();
    }

    @Test(groups = {COSMOS_ID, FILES_ROOT_FOLDER})
    public void testWhenGetFilesRootFolder_thenGetRootContent() {

        String activeUrl = config.getBaseUrl() + config.getFilesUrlApi();
        String actualPage = httpApiClient.getPage(activeUrl, getHeadersXTokenMap(loginToken));

        assertThat("Response doesn't contain ROOT object",
                actualPage, containsString(config.getRootFolderFlag()));

        String idRootFolder = parsePageWithMatcher(actualPage, ID_FOLDER_MATCHER);
        assertThat("Response doesn't match folder id",
                idRootFolder, equalTo("84c966d5-8dce-429d-8f92-44d5e28b1581"));
    //TODO Introduce business logic assertions

    }
}
