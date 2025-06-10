package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.*;

public class ApiTests extends ApiTestBase {

    int validUserId = 2;
    int notValidUserId = 23;

    @Test
    @DisplayName("Запрос данных по несуществующему пользователю")
    void userNotFound404Test() {
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

    @Test
    @DisplayName("Редактирование пользователя")
    void updateUserTest() {
        given()
                .header(FREE_API_KEY_NAME, FREE_API_KEY_VALUE)
                .body("{\"name\": \"morpheus\", \"job\": \"zion resident\"}")
                .log().uri()
                .log().body()
        .when()
                .put(USERS_END_POINT + validUserId)
        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("zion resident"))
                .body("updatedAt", matchesRegex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"));
    }

    @Test
    @DisplayName("Создание пользователя")
    void createUserTest() {
        given()
                .header(FREE_API_KEY_NAME, FREE_API_KEY_VALUE)
                .body("{\"name\": \"Skrillex\", \"job\": \"artist\"}")
                .log().uri()
                .log().body()
        .when()
                .post(USERS_END_POINT + validUserId)
        .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("id", notNullValue())
                .body("createdAt", matchesRegex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"));
    }

    @Test
    @DisplayName("Удаление пользователя")
    void deleteUserTest() {
        given()
                .header(FREE_API_KEY_NAME, FREE_API_KEY_VALUE)
                .log().uri()
        .when()
                .delete(USERS_END_POINT + validUserId)
        .then()
                .log().status()
                .statusCode(204);
    }

    @Test
    @DisplayName("Регистрация без пароля")
    void tryRegisterWithoutPasswordTest() {
        given()
                .header(FREE_API_KEY_NAME, FREE_API_KEY_VALUE)
                .body("{\"email\": \"sydney@fife.qw\"")
                .log().uri()
                .log().body()
       .when()
                .post(USERS_END_POINT + validUserId)
       .then()
                .log().status()
                .log().body()
                .body("error", equalTo("Missing password"))
                .statusCode(400);
    }










}
