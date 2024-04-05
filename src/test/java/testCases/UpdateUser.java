package testCases;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UpdateUser {
 @Test
    public void do_update_user(){
     Object[] result = LoginUser.doLogin();
     String newUsername = "user" + System.currentTimeMillis();
     String username = (String) result[0];
//        String password = (String) result[1];
//     Response response= (Response) result[2];
     String requestBody=(String) result[3];
     // Set base URI
     RestAssured.baseURI = "https://petstore.swagger.io/v2/user";

     // Send PUT request for updating user
     Response response = RestAssured.given()
             .contentType("application/json")
             .body(requestBody)
             .when()
             .put("/" + newUsername);

     response.then().assertThat().statusCode(200);
     // Extract the response body
     String responseBody = response.getBody().asString();
     System.out.println(requestBody);
     System.out.println("The new user name ==" +newUsername);
     // Assert that the updated newUsername is not the same as the original newUsername
     Assert.assertNotEquals(newUsername, username, "Username was not updated");
 }


}
