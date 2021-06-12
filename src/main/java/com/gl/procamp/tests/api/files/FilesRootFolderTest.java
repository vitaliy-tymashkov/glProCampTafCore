package com.gl.procamp.tests.api.files;

import static com.gl.procamp.helpers.builders.FilesUrlParamsBuilder.setUrlParams;
import static com.gl.procamp.helpers.repository.TestsConstants.ID_FOLDER_MATCHER;
import static com.gl.procamp.helpers.repository.TestsConstants.TOTAL_ITEMS_MATCHER;
import static com.gl.procamp.helpers.repository.TestsGroupConstants.COSMOS_ID;
import static com.gl.procamp.helpers.repository.TestsGroupConstants.FILES_ROOT_FOLDER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Files")
@Feature("Root Folder")
@Severity(value = SeverityLevel.BLOCKER)
public class FilesRootFolderTest extends AbstractFilesRootFolderTest {

    private String loginToken;
    private String idRootFolder;

    @Step("Setup connection and get token")
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

    /**
     * <pre>
     * @step Open Root Folder and assert on items count
     * {@data correct login}
     * {@expect Token created successfully, items count equals to requested items count}
     * </pre>
     */
    @Description(useJavaDoc = true)
    @Story("Count of requested items")
    @Step("Get Root folder and count items content")
    @Test(groups = {COSMOS_ID, FILES_ROOT_FOLDER})
    public void testWhenGetFilesRootFolder_thenGetRootContent() {
        filesUrlParams = setUrlParams(idRootFolder);

        String actualPageWithFolderItems = httpApiClient.getPage(getUrlWithQueryParameters(), getHeadersXTokenMap(loginToken));

        String totalItems = parsePageWithMatcher(actualPageWithFolderItems, TOTAL_ITEMS_MATCHER);

        assertThat("Response items count is not " + filesUrlParams.getLimit(),
                totalItems.substring(0, totalItems.length()-1), equalTo(filesUrlParams.getLimit()));

        // TODO Introduce business logic assertions
    }
}
