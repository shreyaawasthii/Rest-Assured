package test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.hamcrest.Matchers.*;

public class CRUDTest {

    private String userId;
    public static final Logger logger = Logger.getLogger(CRUDTest.class.getName());

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test(priority = 1)
    public void testCreateUser() {
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Tulika");
        requestBody.put("job", "Intern");

        userId = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract().path("id");

        logger.log(Level.INFO, "Created user with ID: " + userId);
    }

    @Test(priority = 2)
    public void testUpdateUser() {
        String requestBody = "{\"name\": \"Tulika Sharma\", \"job\": \"Senior Engineer\"}";

        RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .patch("/users/" + userId)
                .then()
                .statusCode(200);

        logger.log(Level.INFO, "User with ID: " + userId + " updated successfully.");
    }

    @Test(priority = 0)
    public void testFetchUser() {
        RestAssured.given()
                .when()
                .get("/users/" + "5")
                .then()
                .statusCode(200)
                .body("data.id", equalTo(Integer.parseInt(String.valueOf(5))));

        logger.log(Level.INFO, "User fetched successfully.");
    }

    @Test(priority = 3)
    public void testDeleteUser() {
        RestAssured.given()
                .when()
                .delete("/users/" + userId)
                .then()
                .statusCode(204);

        logger.log(Level.INFO, "User with ID: " + userId + " deleted successfully.");
    }

    @Test(priority = 4)
    public void testListUsers() {
        Response response = RestAssured.given()
                .when()
                .get("/users");

        response.then()
                .statusCode(200);

        logger.log(Level.INFO, "List of Users: " + response.getBody().asString());
    }

    @Test(priority = 5)
    public void testRegisterSuccessful() {
        String requestBody = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";

        Response response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/register");

        response.then()
                .statusCode(200);

        logger.log(Level.INFO, "Registration successful. Response: " + response.getBody().asString());
    }
    @Test(priority = 6)
    public void testRegisterUnsuccessful() {
        String requestBody = "{\"email\": \"testUser@xyz\"}";

        Response response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/register");

        response.then()
                .statusCode(400);

        logger.log(Level.INFO, "Registration unsuccessful. Response: " + response.getBody().asString());
    }
    @Test(priority = 7)
    public void testLoginSuccessful() {
        String requestBody = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}";

        Response response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/login");

        response.then()
                .statusCode(200);

        logger.log(Level.INFO, "Login successful. Response: " + response.getBody().asString());
    }
    @Test(priority = 8)
    public void testLoginUnsuccessful() {
        String requestBody = "{\"email\": \"peter@klaven\"}";

        Response response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/login");

        response.then()
                .statusCode(400);

        logger.log(Level.INFO, "Login unsuccessful. Response: " + response.getBody().asString());
    }
    @Test(priority = 8)
    public void testUpdatePut() {
        String requestBody = "{\"name\": \"Tashi Tayal\", \"job\": \"Senior Engineer\"}";

        Response response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put("/users/2");

        response.then()
                .statusCode(200);

        logger.log(Level.INFO, "User updated successfully. Response: " + response.getBody().asString());
    }

    @Test(priority = 9)
    public void testUpdatePatch() {
        String requestBody = "{\"name\": \"John Doe\", \"job\": \"Senior Engineer\"}";

        Response response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .patch("/users/2");

        response.then()
                .statusCode(200);

        logger.log(Level.INFO, "User updated successfully using PATCH request. Response: " + response.getBody().asString());
    }


}
