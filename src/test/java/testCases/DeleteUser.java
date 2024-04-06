package testCases;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteUser {

    @Test
    public void test_delete_user() throws InterruptedException {
        
        Object[] createdUser = CreateUser.doCreateUser();
        String username = (String) createdUser[0];
        String password = (String) createdUser[1];
        String requestBody = (String) createdUser[3];
        System.out.println("username"+username);
        String url = "https://petstore.swagger.io/v2/user/" + username;
        System.out.println("url"+url);

        // Set base URI for login endpoint
        RestAssured.baseURI = url;
        // Store the username before deleting the user
//        String deletedUsername = username;
        System.out.println("delllllllllll"+username);
        // Send DELETE request for deleting user
        Response response = RestAssured.given()
                .when()
                .delete();

        // Assert the status code
        response.then().assertThat().statusCode(200);

        // Print response details
        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("Status Code: " + response.getStatusCode());
        Thread.sleep(3000);
        // Perform login using the username of the deleted user
        Object[] resultAfterDelete = LoginUser.doLogin(username, password, requestBody);
        System.out.println(requestBody);
        System.out.println("+++++"+resultAfterDelete[2]);

    }
}
