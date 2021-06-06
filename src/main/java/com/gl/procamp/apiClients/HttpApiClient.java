package com.gl.procamp.apiClients;

import static com.gl.procamp.tests.repository.ApiCallsConstants.ACCEPT;
import static com.gl.procamp.tests.repository.ApiCallsConstants.APP_JSON_TEXT_JS_ALL_ALL;
import static com.gl.procamp.tests.repository.ApiCallsConstants.CONNECTION_TIMEOUT;
import static com.gl.procamp.tests.repository.ApiCallsConstants.DELETE_REQUEST;
import static com.gl.procamp.tests.repository.ApiCallsConstants.GET_REQUEST;
import static com.gl.procamp.tests.repository.ApiCallsConstants.POST_REQUEST;
import static com.gl.procamp.tests.repository.ApiCallsConstants.PUT_REQUEST;
import static com.gl.procamp.tests.repository.ApiCallsConstants.READ_TIMEOUT;
import static com.gl.procamp.tests.repository.ApiCallsConstants.TEXT_HTML;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.fail;
import static org.testng.AssertJUnit.assertFalse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.gl.procamp.tests.model.AuthResponse;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpApiClient extends AbstractHttpApiClient {
    private static final Logger logger = LoggerFactory.getLogger(HttpApiClient.class);
    private HttpURLConnection connection;

    public int getStatusCode(String activeUrl) {
        try {
            getConnection(activeUrl);

            int actualStatusCode = connection.getResponseCode();
            logger.info("Actual Status Code for {} is {}", activeUrl, actualStatusCode);

            return actualStatusCode;
        } catch (IOException e) {
            fail("Failed with IOException: " + e.getMessage());
        }
        return 0;
    }

    public String getLoginToken() {
        String activeUrl = config.getBaseUrl() + config.getLoginUrlApi();
        String actualLoginToken = null;
        try {
            getConnectionWithPostForLogin(activeUrl);

            int actualStatusCode = connection.getResponseCode();
            assertThat("Status code is not 200", actualStatusCode, is(200));
            String response = getResponseText(actualStatusCode, connection);

            actualLoginToken = parseResponseExtractLoginToken(response);
            logger.info("Actual Login Token  for {} is {}", activeUrl, actualLoginToken);

            assertFalse("Login Token is empty", actualLoginToken.isEmpty());
            logger.debug("Login token = " + actualLoginToken);
        } catch (IOException e) {
            logger.error("Failed with IOException {}", e.getMessage());
            fail("Failed with IOException: " + e.getMessage());
        }
        return actualLoginToken;
    }

    private String parseResponseExtractLoginToken(String response) {
        AuthResponse authResponse = new Gson().fromJson(response, AuthResponse.class);
        String actualLoginToken = authResponse.getToken();
        return actualLoginToken;
    }

    public String getIncorrectLoginText(String activeUrl) {
        try {
            getConnectionWithPostForIncorrectLogin(activeUrl);

            int actualStatusCode = connection.getResponseCode();
            assertThat("Status code is not 401", actualStatusCode, is(401));
            return getErrorStreamText();
        } catch (IOException e) {
            logger.error("Failed with IOException {}", e.getMessage());
            fail("Failed with IOException: " + e.getMessage());
        }
        return null;
    }

    private String getErrorStreamText() throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }
        return total.toString();
    }

    public String getPage(String activeUrl) {
        try {
            getConnection(activeUrl);
            int actualStatusCode = connection.getResponseCode();
            BufferedReader streamReader = getStreamForActualStatusCode(actualStatusCode);
            StringBuilder content = getContent(streamReader);
            streamReader.close();

            return content.toString();
        } catch (IOException e) {
            logger.error("Failed with IOException {}", e.getMessage());
            fail("Failed with IOException: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            logger.error("Failed to find title {}", e.getMessage());
            fail("Failed to find title: " + e.getMessage());
        }
        return null;
    }

    private StringBuilder getContent(BufferedReader streamReader) throws IOException {
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = streamReader.readLine()) != null) {
            content.append(inputLine);
        }
        return content;
    }

    private BufferedReader getStreamForActualStatusCode(int actualStatusCode) throws IOException {
        BufferedReader streamReader;
        if (actualStatusCode > 299) {
            streamReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        } else {
            streamReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }
        return streamReader;
    }

    private void getConnection(String activeUrl) throws IOException {
        URL url = new URL(activeUrl);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(GET_REQUEST);
        connection.setRequestProperty(ACCEPT, TEXT_HTML);
        connection.setConnectTimeout(CONNECTION_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
    }

    private void getConnectionWithPost(String activeUrl) throws IOException {
        URL url = new URL(activeUrl);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(POST_REQUEST);
        connection.setRequestProperty(ACCEPT, TEXT_HTML);
        connection.setConnectTimeout(CONNECTION_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
    }

    private void getConnectionWithPostForLogin(String activeUrl) throws IOException {
        setPostHeaders(activeUrl);
        setAuthHeader(connection);
        setBodyForLoginApi(connection);
    }

    private void getConnectionWithPostForIncorrectLogin(String activeUrl) throws IOException {
        setPostHeaders(activeUrl);
        setAuthHeaderWithIncorrectCredentials(connection);
        setBodyForLoginApi(connection);
    }

    private void setPostHeaders(String activeUrl) throws IOException {
        URL url = new URL(activeUrl);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(POST_REQUEST);
        connection.setRequestProperty(ACCEPT, APP_JSON_TEXT_JS_ALL_ALL);
        connection.setConnectTimeout(CONNECTION_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
        connection.setUseCaches(false);
        connection.setDoOutput(true); // indicates POST method
        connection.setDoInput(true);
    }

    private void getConnectionWithPut(String activeUrl) throws IOException {
        URL url = new URL(activeUrl);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(PUT_REQUEST);
        connection.setRequestProperty(ACCEPT, TEXT_HTML);
        connection.setConnectTimeout(CONNECTION_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
    }

    private void getConnectionWithDelete(String activeUrl) throws IOException {
        URL url = new URL(activeUrl);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(DELETE_REQUEST);
        connection.setRequestProperty(ACCEPT, TEXT_HTML);
        connection.setConnectTimeout(CONNECTION_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
    }

    public void disconnect() {
        connection.disconnect();
    }
}
