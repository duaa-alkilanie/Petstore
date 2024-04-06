package testCases;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.testng.Assert.assertNotNull;

public class CreateUser {

        @Test
        public void testUserCreation() {
                // Call the doCreateUser() method to create a user and retrieve the response
                Object[] result = CreateUser.doCreateUser();
                Response response = (Response) result[2];

                // Assert the status code of the response
                response.then().assertThat().statusCode(200);

                // Extract the "message" value from the response
                String message = response.then().extract().path("message");

                // Ensure that the extracted message is not null
                assertNotNull(message, "The message in the response is not null");
        }

        public static Object[] doCreateUser() {
                // Set base URI
                RestAssured.baseURI = "https://petstore.swagger.io/v2/user";

                // Generate dynamic data
                String username = "user" + System.currentTimeMillis();
                String email = "user" + System.currentTimeMillis() + "@example.com";
                String phone = "002011" + System.currentTimeMillis();
                String password = "DuaaTester";
                // Define the request body with dynamic data
                String requestBody = "{\n" +
                                "  \"id\": 0,\n" +
                                "  \"username\": \"" + username + "\",\n" +
                                "  \"firstName\": \"akram\",\n" +
                                "  \"lastName\": \"alkilani\",\n" +
                                "  \"email\": \"" + email + "\",\n" +
                                "  \"password\": \"" + password + "\",\n" +
                                "  \"phone\": \"" + phone + "\",\n" +
                                "  \"userStatus\": 0\n" +
                                "}";
                System.out.println(requestBody);

                // Send POST request and validate response
                Response response = RestAssured.given()
                                .contentType(ContentType.JSON)
                                .body(requestBody)
                                .when()
                                .post()
                                .then().log().all().extract().response();
                System.out.println("the create user done successfully ");
                // Assert on the status code
                response.then().assertThat().statusCode(200);

                // Return the generated username, password, and the entire response
                return new Object[] { username, password, response, requestBody };
        }
}
