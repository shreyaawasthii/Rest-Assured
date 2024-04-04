package apiTest;

import crudPages.PatchRequest;
import crudPages.PutRequest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static apiTest.CreateUserTest.logger;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public class UpdateUserTest{

    private PutRequest putRequest;
    private PatchRequest patchRequest;
    private String userId;
    private String token;

    public UpdateUserTest(PutRequest putRequest, PatchRequest patchRequest) {
        this.putRequest = putRequest;
        this.patchRequest = patchRequest;
    }

    @Test
    public void testUpdateSuccessful(String userId) {
        String newName = "New Name";
        String newStatus = "inactive";
        String newEmail = "xyzz@test.com";
        System.out.println("Updating user with ID: {}" + userId);
        System.out.println("New Name: {}"+ newName);
        System.out.println("New Status: {}"+ newStatus);
        System.out.println("New Email: {}"+ newEmail);
        Response response = putRequest.updateSuccessful(userId, newName, newStatus, newEmail);
        response.then()
                .statusCode(200)
                .body("name", equalTo(newName))
                .body("status", equalTo(newStatus));
        logger.info("testUpdateSuccessful passed");
    }

    @Test
    public void testUpdateStatus(String userId) {
        String newStatus = "active";
        Response response = putRequest.updateStatus(userId, newStatus);
        response.then()
                .statusCode(200)
                .body("status", equalTo(newStatus));
        logger.info("testUpdateStatus passed");
    }

    @Test
    public void testUpdateSpecificField(String userId) {
        String fieldName = "name";
        String fieldValue = "Updated Name";
        Response response = putRequest.updateSpecificField(userId, fieldName, fieldValue);
        response.then()
                .statusCode(200)
                .body("name", equalTo(fieldValue));
        logger.info("testUpdateSpecificField passed");
    }

    @Test
    public void testUpdateNonExistingUser() {
        String newName = "New Name";
        String newStatus = "active";
        int nonExistingUserId = 9999; // Assuming this user does not exist
        Response response = putRequest.updateNonExistingUser(nonExistingUserId, newName, newStatus);
        response.then()
                .statusCode(404);
        logger.info("testUpdateNonExistingUser passed");
    }
        @Test
        public void testUpdateWithMissingDetails(String userId) {
            Map<String, Object> updatedData = new HashMap<>();
            updatedData.put("name", "");
            updatedData.put("email", "");
            Response response = patchRequest.updateWithMissingDetails(userId, updatedData);
            response.then()
                    .statusCode(422);
            String actualMessage = response.jsonPath().getString("message");
            String expectedMessage = "[can't be blank, can't be blank]";

            assertEquals(actualMessage, expectedMessage,
                    "Expected message '" + expectedMessage + "' does not match the actual message: '" + actualMessage + "'");
            logger.info("testUpdateWithMissingDetails passed");
        }

        @Test
        public void testUpdateInvalidFormat(String userId) {
            Map<String, Object> updatedData = new HashMap<>();
            updatedData.put("email", "invalidemail");
            Response response = patchRequest.updateInvalidFormat(userId, updatedData);
            response.then()
                    .statusCode(422);
            assertEquals(response.getStatusCode(), 422, "Status code is not as expected");
            String actualMessage = response.jsonPath().getString("message");
            String expectedMessage = "[can't be blank, is invalid]";

            assertEquals(actualMessage, expectedMessage,
                    "Expected message '" + expectedMessage + "' does not match the actual message: '" + actualMessage + "'");
            logger.info("testUpdateInvalidFormat passed");
        }

        @Test
        public void testUpdateSameData(String userId) {
            Map<String, Object> updatedData = new HashMap<>();
            updatedData.put("name", "Tashi Dixit");
            updatedData.put("email", "tashi.dixita@gmail.com");
            Response response = patchRequest.updateSameData(userId, updatedData);
            response.then()
                    .statusCode(422)
                    .body("message", equalTo("has already been taken"));
            logger.info("testUpdateSameData passed");
        }

        @Test
        public void testUpdateNull(String userId) {
            Map<String, Object> updatedData = new HashMap<>();
            updatedData.put("name", null);
            updatedData.put("email", null);
            Response response = patchRequest.updateNull(userId, updatedData);
            response.then()
                    .statusCode(422);
            String actualMessage = response.jsonPath().getString("message");
            String expectedMessage = "[can't be blank, can be male of female]";

            assertEquals(actualMessage, expectedMessage,
                    "Expected message '" + expectedMessage + "' does not match the actual message: '" + actualMessage + "'");
            logger.info("testUpdateNull passed");
        }
    }
