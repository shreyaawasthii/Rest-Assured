package crudPages;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PostRequests {

    private String token;

    public PostRequests(String token) {
        this.token = token;
    }

    public Response createUserWithValidData(String name, String gender, String email,String status) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body("{\"name\":\"" + name + "\", \"gender\":\"" + gender + "\", \"email\":\"" + email + "\", \"status\":\"" + status + "\"}")
                .when()
                .post("/public/v2/users");
    }

    public Response createUserMissingDetails(String name, String email, String status) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body("{\"name\":\"" + name + "\", \"email\":\"" + email + "\", \"status\":\"" + status + "\"}")
                .when()
                .post("/public/v2/users");
    }

    public Response createUserInvalidFormat(String name, String gender, String email) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body("{\"name\":\"" + name + "\", \"gender\":\"" + gender + "\", \"email\":\"" + email + "\"}")
                .when()
                .post("/public/v2/users");
    }

    public Response createUserWithExistingEmail(String name, String gender, String email, String status) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body("{\"name\":\"" + name + "\", \"gender\":\"" + gender + "\", \"email\":\"" + email + "\", \"status\":\"" + status + "\"}")
                .when()
                .post("/public/v2/users");
    }

    public Response createUserAdditionalFields(String name, String gender, String email, String status, String extraField) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body("{\"name\":\"" + name + "\", \"gender\":\"" + gender + "\", \"email\":\"" + email + "\", \"status\":\"" + status + "\", \"extraField\":\"" + extraField + "\"}")
                .when()
                .post("/public/v2/users");
    }
}
