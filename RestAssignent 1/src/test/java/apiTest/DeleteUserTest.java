package apiTest;

import crudPages.DeleteRequest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static apiTest.CreateUserTest.logger;

public class DeleteUserTest {

    private DeleteRequest deleteRequest;
    private CreateUserTest createUserTest;

    public DeleteUserTest(DeleteRequest deleteRequest) {
        this.deleteRequest = deleteRequest;
    }

    @Test
    public void testDeleteUserById(String userId) {
        Response response = deleteRequest.deleteUserById("");
        response.then()
                .statusCode(204);
        logger.info("testDeleteUserById passed");
    }

    @Test
    public void testDeleteUserByName() {
        String name = "Tashi";
        Response response = deleteRequest.deleteUserByName(name);
        response.then()
                .statusCode(404);
        logger.info("testDeleteUserByName passed");
    }

    @Test
    public void testDeleteUserWithQueryParams() {
        String userEmail = "existing.email@gmail.com";
        String userStatus = "active";
        Response response = deleteRequest.deleteUserWithQueryParams(userEmail, userStatus);
        response.then()
                .statusCode(404);
        logger.info("testDeleteUserWithQueryParams passed");
    }

    @Test
    public void testDeleteNonExistingUser() {
        String nonExistingUserId = "999999"; // Assuming this user does not exist
        Response response = deleteRequest.deleteUserById(nonExistingUserId);
        response.then()
                .statusCode(404);
        logger.info("testDeleteNonExistingUser passed");
    }

}