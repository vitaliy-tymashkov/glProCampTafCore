package com.gl.procamp.tests.model.files;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class FilesUrlParams {
    private String breadcrumbs;
    private String offset;
    private String limit;
    private String folderId;
    private String underscore;

    public Map<String, String> build() {
        Map<String, String> urlParamsMap = new HashMap<>();
        urlParamsMap.put("breadcrumbs", breadcrumbs);
        urlParamsMap.put("offset", offset);
        urlParamsMap.put("limit", limit);
        urlParamsMap.put("folder_id", folderId);
        urlParamsMap.put("_", underscore);

        return urlParamsMap;
    }

    public FilesUrlParams withBreadcrumbs(String breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
        return this;
    }

    public FilesUrlParams withOffset(String offset) {
        this.offset = offset;
        return this;
    }

    public FilesUrlParams withLimit(String limit) {
        this.limit = limit;
        return this;
    }

    public FilesUrlParams withFolderId(String folderId) {
        this.folderId = folderId;
        return this;
    }

    public FilesUrlParams withUnderscore(String underscore) {
        this.underscore = underscore;
        return this;
    }
}
