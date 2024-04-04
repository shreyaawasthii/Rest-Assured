package crudPages;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

public class PatchRequest {

    private String token;

    public PatchRequest(String token) {
        this.token = token;
    }

    public Response updateWithMissingDetails(String userId, Map<String, Object> updatedData) {
        return updateUser(userId, updatedData);
    }

    public Response updateInvalidFormat(String userId, Map<String, Object> updatedData) {
        return updateUser(userId, updatedData);
    }

    public Response updateSameData(String userId, Map<String, Object> updatedData) {
        return updateUser(userId, updatedData);
    }

    public Response updateNull(String userId, Map<String, Object> updatedData) {
        return updateUser(userId, updatedData);
    }

    public Response updateUser(String userId, Map<String, Object> updatedData) {
        return RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .pathParam("userId", userId)
                .contentType("application/json")
                .body(updatedData)
                .patch("/public/v2/users/{userId}");
    }
}
