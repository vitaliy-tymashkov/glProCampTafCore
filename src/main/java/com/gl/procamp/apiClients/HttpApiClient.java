package com.gl.procamp.apiClients;

import static com.gl.procamp.config.ConfigConstants.LOGIN_URL;
import static com.gl.procamp.tests.repository.ApiCallsConstants.ACCEPT;
import static com.gl.procamp.tests.repository.ApiCallsConstants.CONNECTION_TIMEOUT;
import static com.gl.procamp.tests.repository.ApiCallsConstants.DELETE_REQUEST;
import static com.gl.procamp.tests.repository.ApiCallsConstants.GET_REQUEST;
import static com.gl.procamp.tests.repository.ApiCallsConstants.POST_REQUEST;
import static com.gl.procamp.tests.repository.ApiCallsConstants.PUT_REQUEST;
import static com.gl.procamp.tests.repository.ApiCallsConstants.READ_TIMEOUT;
import static com.gl.procamp.tests.repository.ApiCallsConstants.TEXT_HTML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpApiClient {
    private static final Logger logger = LoggerFactory.getLogger(HttpApiClient.class);
    private HttpURLConnection connection;

    public int getStatusCode(String activeUrl) throws IOException {
        getConnection(activeUrl);

        int actualStatusCode = connection.getResponseCode();
        logger.info("Actual Status Code for {} is {}", LOGIN_URL, actualStatusCode);

        return actualStatusCode;
    }

    public String getPage(String activeUrl) throws IOException {
        getConnection(activeUrl);
        int actualStatusCode = connection.getResponseCode();
        BufferedReader streamReader = getStreamForActualStatusCode(actualStatusCode);
        StringBuilder content = getContent(streamReader);
        streamReader.close();

        return content.toString();
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
