package crudPages;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetRequests {

    private String token;

    public GetRequests(String token) {
        this.token = token;
    }

    public Response fetchExistingUsers() {
        return given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/public/v2/users");
    }

    public Response fetchNonExistingUser(int userId) {
        return given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/public/v2/users/" + userId);
    }

    public Response fetchWithName(String name) {
        return given()
                .header("Authorization", "Bearer " + token)
                .queryParam("name", name)
                .when()
                .get("/public/v2/users");
    }

    public Response fetchWithQueryParams(String param1, String param2) {
        return given()
                .header("Authorization", "Bearer " + token)
                .queryParam("param1", param1)
                .queryParam("param2", param2)
                .when()
                .get("/public/v2/users");
    }

    public Response fetchUserById(String userId) {
        return given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/public/v2/users/" + userId);
    }
}
