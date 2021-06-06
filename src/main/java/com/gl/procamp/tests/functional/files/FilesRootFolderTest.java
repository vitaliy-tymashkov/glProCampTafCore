package com.gl.procamp.tests.functional.files;

import static com.gl.procamp.tests.builders.FilesUrlParamsBuilder.setUrlParams;
import static com.gl.procamp.tests.repository.TestsConstants.ID_FOLDER_MATCHER;
import static com.gl.procamp.tests.repository.TestsConstants.TOTAL_ITEMS_MATCHER;
import static com.gl.procamp.tests.repository.TestsGroupConstants.COSMOS_ID;
import static com.gl.procamp.tests.repository.TestsGroupConstants.FILES_ROOT_FOLDER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FilesRootFolderTest extends AbstractFilesRootFolderTest {

    private String loginToken;
    private String idRootFolder;


    @BeforeClass(alwaysRun = true)
    public void setUp() {
        super.setUp();
        loginToken = httpApiClient.getLoginToken();

        String activeUrl = config.getBaseUrl() + config.getFilesUrlApi();
        String actualPage = httpApiClient.getPage(activeUrl, getHeadersXTokenMap(loginToken));

        assertThat("Response doesn't contain ROOT object",
                actualPage, containsString(config.getRootFolderFlag()));

        idRootFolder = parsePageWithMatcher(actualPage, ID_FOLDER_MATCHER);
        assertThat("Root folder ID is empty",
                idRootFolder.isEmpty(), equalTo(false));
    }

    @Test(groups = {COSMOS_ID, FILES_ROOT_FOLDER})
    public void testWhenGetFilesRootFolder_thenGetRootContent() {
        filesUrlParams = setUrlParams(idRootFolder);

        String actualPageWithFolderItems = httpApiClient.getPage(getUrlWithQueryParameters(), getHeadersXTokenMap(loginToken));

        String totalItems = parsePageWithMatcher(actualPageWithFolderItems, TOTAL_ITEMS_MATCHER);

        assertThat("Response items count is not " + filesUrlParams.getLimit(),
                totalItems.substring(0, totalItems.length()-1), equalTo(filesUrlParams.getLimit()));

        //TODO Introduce business logic assertions
    }
}
