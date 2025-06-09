package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;

public class ApiTests extends ApiTestBase {

    int validUserId = 2;
    int notValidUserId = 23;

    @Test
    @DisplayName("Запрос данных по несуществующему пользователю")
    void UserNotFound404Test() {
        given()
                .header(FREE_API_KEY_NAME, FREE_API_KEY_VALUE)
                .log().uri()
        .when()
                .get(USERS_END_POINT + notValidUserId)
        .then()
                .log().status()
                .statusCode(HTTP_NOT_FOUND)
                .body(equalTo("{}"));
    }

}
