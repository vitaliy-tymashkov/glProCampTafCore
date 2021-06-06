package com.gl.procamp.tests.functional.smoke;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gl.procamp.tests.functional.BaseTest;

public class AbstractLoginTest extends BaseTest {

    protected String parsePageWithMatcher(String actualPage, String matcherString) {
        String actualLoginPageTitle = null;
        Pattern pattern = Pattern.compile(matcherString);
        Matcher matcher = pattern.matcher(actualPage);
        if (matcher.find()) {
            actualLoginPageTitle = matcher.group(1);
        }
        return actualLoginPageTitle;
    }
}
