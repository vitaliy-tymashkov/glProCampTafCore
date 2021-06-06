package com.gl.procamp.tests.repository;

public class TestsConstants {
    public final static String EXPECTED_LOGIN_PAGE_TITLE = "CosmosID";
    public final static int EXPECTED_STATUS_CODE_SUCCESS = 200;

    public final static String LOGIN_PAGE_MATCHER = "<title>(.*?)</title>";
    public final static String ID_FOLDER_MATCHER = ".*\"id\":\\s*\"(.*?)\".*";
}
