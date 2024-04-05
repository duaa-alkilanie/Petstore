package testCases;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class DeleteUser {

    @Test
            public  void test_delete_user() {
        Object[] result = LoginUser.doLogin();
        String username = (String) result[0];
        String url = "https://petstore.swagger.io/v2/user/" + username;

        // Set base URI for login endpoint
        RestAssured.baseURI = url;
        // Store the username before deleting the user
        String deletedUsername = username;
        // Send DELETE request for deleting user
        Response response = RestAssured.given()
                .when()
                .delete();

        // Assert the status code
        response.then().assertThat().statusCode(200);

        // Print response details
        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("Status Code: " + response.getStatusCode());
        // Perform login using the username of the deleted user
       Object[] resultAfterDelete= LoginUser.doLogin(deletedUsername);



    }
}
