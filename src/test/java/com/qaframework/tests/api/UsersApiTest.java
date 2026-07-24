package com.qaframework.tests.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * API automation tests for the ReqRes "users" collection endpoint.
 *
 * Security note: the API key is NEVER hardcoded in source. It is read at
 * runtime from the REQRES_API_KEY environment variable. Locally, set it in
 * your shell/IDE run configuration. In CI, it is injected via a GitHub
 * Actions repository secret (see .github/workflows/run-tests.yml), so the
 * real key never appears in the codebase or in any log output.
 */
public class UsersApiTest {

    private static final String BASE_URL = "https://reqres.in";
    private static final String ENDPOINT = "/api/test-suite/collections/users/records";

    private String apiKey;

    @BeforeClass
    public void setUp() {
        apiKey = System.getenv("REQRES_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            throw new SkipException(
                "Skipping API tests: REQRES_API_KEY environment variable is not set. " +
                "Set it locally (export REQRES_API_KEY=your_key) or configure it as a " +
                "GitHub Actions secret to run these tests."
            );
        }
        RestAssured.baseURI = BASE_URL;
    }

    @Test(priority = 1, description = "Verify a new user record can be created via POST")
    public void testCreateUserRecord() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Junaid Sayyad");
        requestBody.put("job", "SDET / QA Automation Engineer");

        Response response = RestAssured
                .given()
                    .header("x-api-key", apiKey)
                    .contentType("application/json")
                    .body(requestBody)
                .when()
                    .post(ENDPOINT)
                .then()
                    .extract().response();

        System.out.println("Response Status: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asPrettyString());

        Assert.assertTrue(
                response.getStatusCode() == 200 || response.getStatusCode() == 201,
                "Expected a 200/201 success status but got " + response.getStatusCode()
        );
        Assert.assertNotNull(response.getBody().asString(), "Response body should not be null");
    }

    @Test(priority = 2, description = "Verify request without an API key is rejected with 401")
    public void testCreateUserRecordWithoutApiKey_shouldBeUnauthorized() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "No Auth User");
        requestBody.put("job", "Should Fail");

        Response response = RestAssured
                .given()
                    .contentType("application/json")
                    .body(requestBody)
                .when()
                    .post(ENDPOINT)
                .then()
                    .extract().response();

        Assert.assertEquals(response.getStatusCode(), 401,
                "Expected 401 Unauthorized when no API key is supplied");
        Assert.assertTrue(response.getBody().asString().contains("missing_api_key"),
                "Expected error response to indicate a missing API key");
    }
}
