package com.gl.procamp.tests.functional.files;

import java.util.Map;

import com.gl.procamp.tests.functional.AbstractBaseTest;
import com.gl.procamp.tests.model.files.FilesUrlParams;

public class AbstractFilesRootFolderTest extends AbstractBaseTest {
    protected FilesUrlParams filesUrlParams;

    protected String getUrlWithQueryParameters() {
        String activeUrlWithQueryParameters =
                config.getBaseUrl()
                        + config.getFilesUrlApi()
                        + getQueryParameters(filesUrlParams.build());
        return activeUrlWithQueryParameters;
    }

    protected String getQueryParameters(Map<String, String> urlParams) {
        StringBuilder queryParameters = new StringBuilder();
        if (!urlParams.keySet().isEmpty()) {
            queryParameters.append("?");
            for (Map.Entry<String, String> entry : urlParams.entrySet()) {
                queryParameters.append(entry.getKey());
                queryParameters.append("=");
                queryParameters.append(entry.getValue());
                queryParameters.append("&");
            }
            if( queryParameters.length() > 0 )
                queryParameters.setLength(queryParameters.length() - 1);
        }
        return queryParameters.toString();
    }
}
