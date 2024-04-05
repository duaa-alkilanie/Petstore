package testCases;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.containsString;

public class LoginUser {
    @Test
    public void loginRequest() {
    Object[] result = LoginUser.doLogin();
//        String username = (String) result[0];
//        String password = (String) result[1];
        Response response= (Response) result[3];

        // Assert the status code of the response
        response.then().assertThat().statusCode(200);
        //Assert the message to containsString
        response.then().assertThat().body("message", containsString("logged in user session"));
        // Print response details
        System.out.println(response.getBody().asString());
        System.out.println(response.statusCode());
    }

    public static Object[] doLogin() {


        Object[] result = CreateUser.doCreateUser();
        // Create user and retrieve credentials and response
//        Object[] result = CreateUser.doCreateUser();
        String username = (String) result[0];
        String password = (String) result[1];
        String requestBody= (String) result[3];
        // Set base URI for login endpoint
        RestAssured.baseURI = "https://petstore.swagger.io/v2/user";

        // Send GET request for login
        Response response = RestAssured.given()
                .param("username", username)
                .param("password", password)
                .when()
                .get("/login")
                .then()
                .extract()
                .response();
        System.out.println("the login user done successfully ");

        return new Object[]{username,password,response,requestBody};

    }
}








