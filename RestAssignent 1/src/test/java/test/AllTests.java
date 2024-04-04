package test;

import apiTest.*;
import crudPages.*;
import filters.RequestLoggingFilter;
import io.restassured.RestAssured;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class AllTests {

    private PostRequests postRequests;
    private GetRequests getRequests;
    private PutRequest putRequest;
    private PatchRequest patchRequest;
    private DeleteRequest deleteRequest;
    private String token;
    private static final Logger logger = LogManager.getLogger(AllTests.class);

    @BeforeClass
    public void setUp() {
        token = "cc21769caac6e02a8c3899d88396a5582680a7c1430e4e871cd2c3f1664f35b2";
        RestAssured.baseURI = "https://gorest.co.in/";
        RestAssured.filters(new RequestLoggingFilter());
        postRequests = new PostRequests(token);
        getRequests = new GetRequests(token);
        putRequest = new PutRequest(token);
        patchRequest = new PatchRequest(token);
        deleteRequest = new DeleteRequest(token);
    }

    @Test
    public void runAllTests() {
        CreateUserTest createUserTest = new CreateUserTest(postRequests);
        GetUserTest getUserTest = new GetUserTest(getRequests, createUserTest);
        UpdateUserTest updateUserTest = new UpdateUserTest(putRequest, patchRequest);
        DeleteUserTest deleteUserTest = new DeleteUserTest(deleteRequest);

        createUserTest.testCreateUserWithValidData();
        if (createUserTest.getUserId() != null) {
            String userId = createUserTest.getUserId();
            getUserTest.fetchSingleUserFromPostRequest(userId);

            updateUserTest.testUpdateSuccessful(userId);
            updateUserTest.testUpdateStatus(userId);
            updateUserTest.testUpdateSpecificField(userId);
            updateUserTest.testUpdateWithMissingDetails(userId);
            updateUserTest.testUpdateInvalidFormat(userId);
            updateUserTest.testUpdateSameData(userId);
            updateUserTest.testUpdateNull(userId);

            deleteUserTest.testDeleteUserById(userId);
        } else {
            logger.error("User creation test failed, unable to retrieve userId.");
        }

        createUserTest.testCreateUserMissingDetails();
        createUserTest.testCreateUserInvalidFormat();
        createUserTest.testCreateUserWithExistingEmail();
        createUserTest.testCreateUserAdditionalField();

        getUserTest.testFetchExistingUsers();
        getUserTest.testFetchNonExistingUser();
        getUserTest.testFetchWithName();
        //getUserTest.testFetchWithQueryParams();

        updateUserTest.testUpdateNonExistingUser();

        deleteUserTest.testDeleteUserByName();
        deleteUserTest.testDeleteUserWithQueryParams();
        deleteUserTest.testDeleteNonExistingUser();
    }

 }
