package apiTest;

import crudPages.PostRequests;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public class CreateUserTest{

    private PostRequests postRequests;
    private String userId;
    public static final Logger logger = LogManager.getLogger(CreateUserTest.class);
    private String expectedEmail;
    private String expectedName;

    public CreateUserTest(PostRequests postRequests) {
        this.postRequests = postRequests;
    }

    public String getUserId() {
        return userId;
    }

    public String getExpectedName() {
        return expectedName;
    }

    public String getExpectedEmail() {
        return expectedEmail;
    }

    @Test
    public void testCreateUserWithValidData() {
        String name = "Tashi Dixit";
        String gender = "female";
        String email = "tashi.ddixit@gmail.com";
        String status = "active";

        io.restassured.response.Response response = postRequests.createUserWithValidData(name, gender, email, status);

        logger.info("Response Body: " + response.getBody().asString());

        int statusCode = response.getStatusCode();
        logger.info("Actual Status Code: " + statusCode);

        if (statusCode != 201) {
            logger.error("User creation failed with status code " + statusCode + ": " + response.getBody().asString());
        } else {
            response.then()
                    .statusCode(201)
                    .body("name", equalTo(name))
                    .body("email", equalTo(email));
            expectedName = response.jsonPath().getString("name");
            expectedEmail = response.jsonPath().getString("email");
            userId = response.jsonPath().getString("id");
            if (userId != null) {
                logger.info("Created user ID: " + userId);
            } else {
                logger.error("Failed to extract user ID from the response.");
            }
        }    }


    @Test
    public void testCreateUserMissingDetails() {
        String name = "Tashika Dixit";
        String email = "tashika.dixita@gmail.com";
        String status = "inactive";
        io.restassured.response.Response response = postRequests.createUserMissingDetails(name, email, status);
        assertEquals(response.getStatusCode(), 422, "Status code is not as expected");
        String actualMessage = response.jsonPath().getString("message");
        String expectedMessage = "[can't be blank, can be male of female]";

        assertEquals(actualMessage, expectedMessage,
                "Expected message '" + expectedMessage + "' does not match the actual message: '" + actualMessage + "'");
        logger.info("testCreateUserMissingDetails passed");
    }

    @Test
    public void testCreateUserInvalidFormat() {
        String name = "Tenali Raman";
        String gender = "female";
        String email = "tenali.raman.com";

        io.restassured.response.Response response = postRequests.createUserInvalidFormat(name, gender, email);
        assertEquals(response.getStatusCode(), 422, "Status code is not as expected");
        String actualMessage = response.jsonPath().getString("message");
        String expectedMessage = "[can't be blank, is invalid]";

        assertEquals(actualMessage, expectedMessage,
                "Expected message '" + expectedMessage + "' does not match the actual message: '" + actualMessage + "'");
        logger.info("testCreateUserInvalidFormat passed");
    }

    @Test
    public void testCreateUserWithExistingEmail() {
        String name = "Test User";
        String gender = "male";
        String email = "existing.email@gmail.com";
        String status = "active";

        io.restassured.response.Response response = postRequests.createUserWithExistingEmail(name, gender, email, status);
        assertEquals(response.getStatusCode(), 422, "Status code is not as expected");
        String errorMessage = response.jsonPath().getString("message");
        assertThat(errorMessage, containsString("[has already been taken]"));
        logger.info("testCreateUserWithExistingEmail passed");
    }

    @Test
    public void testCreateUserAdditionalField() {
        String name = "Test User 01";
        String gender = "male";
        String email = "testuser01@gmail.com";
        String status = "active";
        String extraField = "something";

        io.restassured.response.Response response = postRequests.createUserAdditionalFields(name, gender, email, status, extraField);
        assertEquals(response.getStatusCode(), 422, "Status code is not as expected");
        logger.info("testCreateUserAdditionalField passed");
    }
}
