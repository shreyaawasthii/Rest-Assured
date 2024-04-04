package crudPages;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class PutRequest {

    private String token;

    public PutRequest(String token) {
        this.token = token;
    }

    public Response updateSuccessful(String userId, String newName, String newStatus, String newEmail) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body("{ \"name\": \"" + newName + "\", \"status\": \"" + newStatus + "\" , \"email\": \"" + newEmail + "\"}")
                .put("/public/v2/users/" + userId);
    }

    public Response updateStatus(String userId, String newStatus) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body("{ \"status\": \"" + newStatus + "\" }")
                .put("/public/v2/users/" + userId);
    }

    public Response updateSpecificField(String userId, String fieldName, String fieldValue) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body("{ \"" + fieldName + "\": \"" + fieldValue + "\" }")
                .put("/public/v2/users/" + userId);
    }

    public Response updateNonExistingUser(int userId, String newName, String newStatus) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body("{ \"name\": \"" + newName + "\", \"status\": \"" + newStatus + "\" }")
                .put("/public/v2/users/" + userId);
    }
}
