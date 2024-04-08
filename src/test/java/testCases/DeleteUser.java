package testCases;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteUser {

    @Test(priority = 1,description = "test delete the user after create ")
    public void test_delete_user()  {

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
        System.out.println("user name before delete it "+username);
        // Send DELETE request for deleting user
        Response response = RestAssured.given()
                .when()
                .delete();

        // Assert the status code
        response.then().assertThat().statusCode(200);
        System.out.println("User deleted successfully");


        // Print response details
        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("Status Code: " + response.getStatusCode());
        // Perform login using the username of the deleted user
        Object[] resultAfterDelete = LoginUser.doLogin(username, password, requestBody);
        System.out.println(requestBody);
        System.out.println("result delete"+resultAfterDelete[2]);
        response.then().assertThat().statusCode(200);

    }


    @Test(priority = 2,description = "test login with user was delete before")
    public void test_loginWithUserWasDelete(){
        Object[] createdUser = CreateUser.doCreateUser();
        String username = (String) createdUser[0];
        String password = (String) createdUser[1];
        String requestBody = (String) createdUser[3];

        // Send DELETE request for deleting user
        Response response = RestAssured.given()
                .param("username", username)
                .when()
                .delete();


//        Object[] result = LoginUser.doLogin(username, password, requestBody);
//        String responseAfterLogin = (String) result[2];
//        String username1=(String)result[0];

      //   Attempt to login with the deleted user's credentials
        Object[] resultAfterDelete = LoginUser.doLogin(username, password, requestBody);
        Response loginResponse = (Response) resultAfterDelete[2];
        System.out.println("response after delete");

//        System.out.println(responseAfterLogin +username1);
        // Assert that login is not successful
        int statusCodeResult = loginResponse.statusCode();
       Assert.assertNotEquals(statusCodeResult,200,"Login should not be successful after user deletion");

    }
}
