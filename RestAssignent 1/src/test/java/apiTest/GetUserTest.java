package apiTest;

import crudPages.GetRequests;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static apiTest.CreateUserTest.logger;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GetUserTest{

    private GetRequests getRequests;
    private CreateUserTest createUserTest;

    public GetUserTest(GetRequests getRequests, CreateUserTest createUserTest) {
        this.getRequests = getRequests;
        this.createUserTest = createUserTest;
    }


    @Test
    public void testFetchExistingUsers() {
        Response response = getRequests.fetchExistingUsers();
        response.prettyPrint();
        response.then()
                .statusCode(200)
                .body("data", not(empty()));
        logger.info("testFetchExistingUsers passed");
    }

    @Test
    public void testFetchNonExistingUser() {
        int userId = 9999; // Assuming this user does not exist
        Response response = getRequests.fetchNonExistingUser(userId);
        response.then()
                .statusCode(404);
        logger.info("testFetchNonExistingUser passed");
    }

    @Test
    public void testFetchWithName() {
        String name = "Tashi";
        Response response = getRequests.fetchWithName(name);
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200, "Expected status code 200 - Success");
        logger.info("testFetchWithName passed");
    }

    @Test
    public void testFetchWithQueryParams() {
        String userEmail = "new.dixita@gmail.com";
        String userStatus = "active";
        Response response = getRequests.fetchWithQueryParams(userEmail, userStatus);
        response.then()
                .statusCode(200);

        assertTrue(response.getBody().asString().contains(userEmail), "Response does not contain user email");
        assertTrue(response.getBody().asString().contains(userStatus), "Response does not contain user status");
        logger.info("testFetchWithQueryParams passed");
    }

    @Test
    public void fetchSingleUserFromPostRequest(String userId) {
        String expectedName = createUserTest.getExpectedName();
        String expectedEmail = createUserTest.getExpectedEmail();
        Response response = getRequests.fetchUserById(userId);
        response.then()
                .statusCode(200)
                .body("name", equalTo(expectedName))
                .body("email", equalTo(expectedEmail));
        logger.info("fetchSingleUserFromPostRequest passed");
    }
}
