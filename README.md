**Introduction:**
This project comprises REST API tests written in Java using the TestNG framework. The tests are designed to validate the CRUD (Create, Read, Update, Delete) operations on user data for two different APIs: https://gorest.co.in/ and https://reqres.in/.

**Structure:**
The project consists of the following packages and classes:

**crudPages Package:**
Contains classes responsible for making HTTP requests for CRUD operations.
Classes: PostRequests, GetRequests, PutRequest, PatchRequest, DeleteRequest

**apiTest Package:**
Contains test classes for CRUD operations and other API functionalities.
Classes: CreateUserTest, GetUserTest, UpdateUserTest, DeleteUserTest, AllTests

**filters Package:**
Contains a custom request logging filter to log request URLs.

**test Package:**
Contains additional test classes for CRUD operations and other API functionalities.

Classes: CRUDTest

Tests for https://gorest.co.in/:
CreateUserTest:
Tests user creation with valid data, missing details, invalid format, existing email, and additional fields.
Validates the response and extracts user details for further tests.

GetUserTest:
Tests fetching existing users, non-existing users, users with specific names, and with query parameters.
Fetches single users based on the ID obtained from CreateUserTest.

UpdateUserTest:
Tests updating user details with successful updates, status update, updating specific fields, and handling various error scenarios.

DeleteUserTest:
Tests deleting users by ID, name, and with query parameters.
Handles scenarios for deleting non-existing users.

Tests for https://reqres.in/:
CRUDTest:
Tests user creation, update, fetch, and deletion operations.
Validates user registration and login functionalities.
Covers both successful and unsuccessful scenarios for registration and login.
Demonstrates the usage of PUT and PATCH methods for updating user details.

Execution:
Tests are executed using the TestNG framework.
Before executing the tests, ensure proper setup by providing necessary configurations and tokens.
Tests can be run individually or collectively using the AllTests class for https://gorest.co.in/ and CRUDTest class for https://reqres.in/.

Conclusion:
The project demonstrates comprehensive testing of CRUD operations and other functionalities for different APIs. Tests are designed to validate various scenarios (test cases) and handle errors effectively. Additionally, logging and reporting mechanisms provide insights into test execution and results.

Author:
Shreya Awasthi
