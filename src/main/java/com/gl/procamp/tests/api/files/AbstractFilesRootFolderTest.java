package com.gl.procamp.tests.api.files;

import java.util.Map;

import com.gl.procamp.tests.AbstractBaseTest;
import com.gl.procamp.helpers.model.files.FilesUrlParams;

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
