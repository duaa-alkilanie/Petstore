package testCases;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UpdateUser {
        @Test(priority = 1,description = "do login with new user after  update in his name ")
        public void do_update_user() {
                Object[] createdUser = CreateUser.doCreateUser();
                String username = (String) createdUser[0];
                String password = (String) createdUser[1];
                String requestBody = (String) createdUser[3];

                Object[] result = LoginUser.doLogin(username, password, requestBody);
                String newUsername = "user" + System.currentTimeMillis();
                username = (String) result[0];
                requestBody = (String) result[3];
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
                System.out.println("The new user name ==" + newUsername);
                // Assert that the updated newUsername is not the same as the original
                Assert.assertNotEquals(newUsername, username, "Username was not updated");
        }

        @Test(priority =2,description = "test case for login with old username has update before ")
        public void do_login_with_old_user_after_update(){
                Object[] createdUser = CreateUser.doCreateUser();
                String username = (String) createdUser[0];
                System.out.println("user name in createion ="+username);
                String password = (String) createdUser[1];
                String requestBody = (String) createdUser[3];

                Object[] result = LoginUser.doLogin(username, password, requestBody);
                String newUsername = "user" + System.currentTimeMillis();

                // Set base URI
                RestAssured.baseURI = "https://petstore.swagger.io/v2/user";

                // Send PUT request for updating user
                Response response = RestAssured.given()
                        .contentType("application/json")
                        .body(requestBody)
                        .when()
                        .put("/" + newUsername);
                System.out.println("user name after update="+newUsername);
                response.then().assertThat().statusCode(200);
                Object[] resultBeforeUpdate = LoginUser.doLogin(username, password, requestBody);
                Response loginResponse = (Response) resultBeforeUpdate[2];
                System.out.println( "username before update"+(username = (String) result[0]));
                // Assert that login is not successful
                int statusCodeResult = loginResponse.statusCode();
                Assert.assertNotEquals(statusCodeResult,200,"Login  with old username should not be successful after user updating");

        }



}
