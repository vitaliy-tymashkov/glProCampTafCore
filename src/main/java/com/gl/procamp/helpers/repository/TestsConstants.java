package com.gl.procamp.helpers.repository;

public class TestsConstants {
    public final static String EXPECTED_LOGIN_PAGE_TITLE = "CosmosID";

    public final static String LOGIN_PAGE_MATCHER = "<title>(.*?)</title>";
    public final static String ID_FOLDER_MATCHER = ".*\"id\":\\s*\"(.*?)\".*";
    public final static String TOTAL_ITEMS_MATCHER = ".*\"total\":\\s*(.*)";
}
