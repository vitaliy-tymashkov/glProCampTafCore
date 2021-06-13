package com.gl.procamp.apiClients;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;

import com.gl.procamp.config.Config;
import com.gl.procamp.helpers.KeyStoreUtility;
import com.gl.procamp.helpers.model.AuthResponse;
import com.gl.procamp.helpers.validators.AbstractJsonSchemaValidator;
import com.google.common.primitives.Chars;
import com.google.gson.Gson;

public class AbstractHttpApiClient extends AbstractJsonSchemaValidator {
    protected static final String BASIC_AUTH = "BASIC";
    protected static final String AUTHORIZATION_HEADER = "Authorization";
    protected static final String BASIC_PREFIX = "Basic ";
    protected static final String USER_KS_ALIAS = "user";
    protected static final String PASSWORD_KS_ALIAS = "password";
    protected static final String UTF_8 = "UTF-8";
    protected Config config = Config.getInstance();

    protected void setAuthHeader(HttpURLConnection connection) {
        prepareAuthHeader(connection, KeyStoreUtility.getSecret(USER_KS_ALIAS), KeyStoreUtility.getSecret(PASSWORD_KS_ALIAS));
    }

    protected void setAuthHeaderWithIncorrectCredentials(HttpURLConnection connection) {
        prepareAuthHeader(connection,
                Chars.concat(KeyStoreUtility.getSecret(USER_KS_ALIAS), "_INCORRECT_LOGIN".toCharArray()),
                Chars.concat(KeyStoreUtility.getSecret(PASSWORD_KS_ALIAS), "_INCORRECT_LOGIN".toCharArray()));
    }

    private void prepareAuthHeader(HttpURLConnection connection, char[] user, char[] password) {
        //Usage of strings for sensitive data is not recommended!
//            String user = String.valueOf(KeyStoreUtility.getSecret(USER_ALIAS));
//            String password = String.valueOf(KeyStoreUtility.getSecret(PASSWORD_ALIAS));
//            String auth = user + ":" + password;
//            byte[] encodedAuth0 = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
//            String authHeaderValue0 = "Basic " + new String(encodedAuth0);
        if (config.getAuthType().equalsIgnoreCase(BASIC_AUTH)) {
            byte[] bytes = toBytes(Chars.concat(user, ":".toCharArray(), password));
            byte[] encodedAuth = Base64.getEncoder().encode(bytes);

            String authHeaderValue = BASIC_PREFIX + new String(encodedAuth);
            connection.setRequestProperty(AUTHORIZATION_HEADER, authHeaderValue);
        }
    }

    protected void setBodyForLoginApi(HttpURLConnection connection) throws IOException {
        String bodyRaw = "expiry=" + config.getExpiry() + "&" + "login_from=" + config.getLoginFrom();
        byte[] result = null;
        try {
            result = bodyRaw.getBytes(UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        connection.getOutputStream().write(result);
    }

    protected String getResponseText(int actualStatusCode, HttpURLConnection connection) throws IOException {
        String response;
        if (actualStatusCode == HttpURLConnection.HTTP_OK) {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = connection.getInputStream().read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            response = result.toString("utf-8");
        } else {
            throw new IOException("Server returned non-OK status: " + actualStatusCode);
        }
        return response;
    }

    protected byte[] toBytes(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = Charset.forName(UTF_8).encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
                byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
        return bytes;
    }

    protected String parseResponseExtractLoginToken(String response) {
        AuthResponse authResponse = new Gson().fromJson(response, AuthResponse.class);
        String actualLoginToken = authResponse.getToken();
        return actualLoginToken;
    }
}
