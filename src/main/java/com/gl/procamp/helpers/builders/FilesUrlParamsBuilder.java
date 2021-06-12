package com.gl.procamp.helpers.builders;

import com.gl.procamp.helpers.model.files.FilesUrlParams;

public class FilesUrlParamsBuilder {
    public static FilesUrlParams setUrlParams(String idRootFolder) {
        return new FilesUrlParams()
                .withBreadcrumbs("1")
                .withOffset("0")
                .withLimit("10")
                .withFolderId(idRootFolder)
                .withUnderscore("1622700773180");
    }

}
