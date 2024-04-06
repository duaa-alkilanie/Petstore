package testCases;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.containsString;

public class LoginUser {
    @Test
    public void loginRequest() {
        Object[] createdUser = CreateUser.doCreateUser();
        String username = (String) createdUser[0];
        String password = (String) createdUser[1];
        String requestBody = (String) createdUser[3];

        Object[] result = LoginUser.doLogin(username, password, requestBody);
        Response response = (Response) result[2];

        // Assert the status code of the response
        response.then().assertThat().statusCode(200);
        // Assert the message to containsString
        response.then().assertThat().body("message", containsString("logged in user session"));
        // Print response details
        System.out.println(response.getBody().asString());
        System.out.println(response.statusCode());
    }

    public static Object[] doLogin(String loginUserName, String LoginPassword, String requestBody) {

        // Set base URI for login endpoint
        RestAssured.baseURI = "https://petstore.swagger.io/v2/user";

        // Send GET request for login
        Response response = RestAssured.given()
                .param("username", loginUserName)
                .param("password", LoginPassword)
                .when()
                .get("/login")
                .then()
                .extract()
                .response();
        System.out.println("the login user done successfully ");
        String responseBody = response.getBody().asString();


        return new Object[] { loginUserName, LoginPassword, responseBody, requestBody };

    }
}
