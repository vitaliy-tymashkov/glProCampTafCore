package com.gl.procamp.tests.functional.smoke;

import static com.gl.procamp.tests.repository.TestsConstants.LOGIN_PAGE_MATCHER;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gl.procamp.tests.functional.BaseTest;

public class AbstractLoginTest extends BaseTest {

    protected String getActualLoginPageTitle(String actualPage) {
        String actualLoginPageTitle = null;
        Pattern pattern = Pattern.compile(LOGIN_PAGE_MATCHER);
        Matcher matcher = pattern.matcher(actualPage);
        if (matcher.find()) {
            actualLoginPageTitle = matcher.group(1);
        }
        return actualLoginPageTitle;
    }
}
