package crudPages;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DeleteRequest {
    private String token;

    public DeleteRequest(String token) {
        this.token = token;
    }

    public Response deleteUserById(String userId) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/public/v2/users/" + userId);
    }

    public Response deleteUserByName(String name) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .queryParam("name", name)
                .when()
                .delete("/public/v2/users");
    }

    public Response deleteUserWithQueryParams(String userEmail, String userStatus) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .queryParam("email", userEmail)
                .queryParam("status", userStatus)
                .when()
                .delete("/public/v2/users");
    }
}
