package com.gl.procamp.helpers.validators;

import static java.util.stream.Collectors.joining;
import static org.testng.Assert.fail;

import java.io.InputStream;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import io.qameta.allure.Step;

public abstract class AbstractJsonSchemaValidator {

    private static final String WHITESPACE = " ";

    @Step("Validate json schema using {jsonSchema}")
    protected static void validateJsonSchema(String response, String jsonSchema) {
        try (
                InputStream schemaStream = inputStreamFromClasspath(jsonSchema)
        ) {
            Set<ValidationMessage> validationResult = validateResponseWithJsonSchema(response, schemaStream);

            checkValidationResults(validationResult);
        } catch (Exception e) {
            fail("Schema validation failed with exception " + e.getMessage());
        }
    }

    private static Set<ValidationMessage> validateResponseWithJsonSchema(String response, InputStream schemaStream) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);

        JsonNode json = objectMapper.readTree(response);
        JsonSchema schema = schemaFactory.getSchema(schemaStream);
        return schema.validate(json);
    }

    private static void checkValidationResults(Set<ValidationMessage> validationResult) {
        if (!validationResult.isEmpty()) {
            String failMessage = validationResult.stream()
                    .map(ValidationMessage::getMessage)
                    .collect(joining(WHITESPACE));
            fail("Schema validation failed with errors " + failMessage);
        }
    }

    private static InputStream inputStreamFromClasspath(String path) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }
}
