package tests;

import models.user_changes.UserBodyModel;
import models.registration.UserRegistrationModel;
import models.user_changes.UserCreationResponseModel;
import models.user_changes.UserResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static specs.BaseSpec.*;

@Tag("API")
public class ApiImprovedTests extends ApiTestBase {

    int validUserId = 2;
    int notValidUserId = 23;


    @Test
    @DisplayName("Запрос данных по несуществующему пользователю")
    void userNotFound404ImprovedTest() {
        step("Делаем запрос по несуществующему пользователю", () ->
                given(RequestSpec)
                .when()
                        .get(USERS_END_POINT + notValidUserId)
                .then()
                        .spec(responseSpec(404))
                        .body(equalTo("{}")));
    }

    @Test
    @DisplayName("Редактирование пользователя")
    void updateUserImprovedTest() {

        UserBodyModel userPutData = new UserBodyModel();
        userPutData.setName("Skrillex");
        userPutData.setJob("Producer");

        UserResponseModel response = step("Отправляем запрос на редактирование пользователя", () ->
                given(RequestSpec)
                        .body(userPutData)
                .when()
                        .put(USERS_END_POINT + validUserId)
                .then()
                        .spec(responseSpec(200))
                        .extract().as(UserResponseModel.class)
        );
        step("Проверяем значения в ответе", () -> {
            assertEquals("Skrillex", response.getName(), "Имя пользователя должно совпадать");
            assertEquals("Producer", response.getJob(), "Должность пользователя должна совпадать");
            assertTrue(response.getUpdatedAt().matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"),
                    "Поле updatedAt должно соответствовать формату даты"
            );
        });
    }

    @Test
    @DisplayName("Создание пользователя")
    void createUserImprovedTest() {

        UserBodyModel userPutData = new UserBodyModel();
        userPutData.setName("Skrillex");
        userPutData.setJob("Producer");

        step("Отправляем запрос на создание пользователя", () ->
                given(RequestSpec)
                        .body(userPutData)
                .when()
                        .post(USERS_END_POINT + validUserId)
                .then()
                        .spec(responseSpec(201))
                        .extract().as(UserCreationResponseModel.class));
    }

    @Test
    @DisplayName("Удаление пользователя")
    void deleteUserImprovedTest() {

        step("Отправляем запрос на удаление пользователя", () ->
                given(RequestSpec)
                .when()
                        .delete(USERS_END_POINT + validUserId)
                .then()
                        .spec(responseSpec(204)));
    }

    @Test
    @DisplayName("Регистрация без пароля")
    void tryRegisterWithoutPasswordImprovedTest() {

        UserRegistrationModel userCreateData = new UserRegistrationModel();
        userCreateData.setName("Skrillex");

        step("Отправляем запрос на регистрацию без пароля", () ->
                given(RequestSpec)
                        .body(userCreateData)
                .when()
                        .post(USERS_END_POINT + validUserId)
                .then()
                        .spec(responseSpec(400))
                        .body("error", equalTo("Missing password")));
    }

    @Test
    @DisplayName("Редактирование пользователя")
    void updateUserWithPatchMethodImprovedTest() {

        UserBodyModel userPutData = new UserBodyModel();
        userPutData.setName("Skrillex");
        userPutData.setJob("Producer");

        UserResponseModel response = step("Отправляем запрос на редактирование пользователя", () ->
                given(RequestSpec)
                        .body(userPutData)
                .when()
                        .patch(USERS_END_POINT + validUserId)
                .then()
                        .spec(responseSpec(200))
                        .extract().as(UserResponseModel.class)
        );
        step("Проверяем значения в ответе", () -> {
            assertEquals("Skrillex", response.getName(), "Имя пользователя должно совпадать");
            assertEquals("Producer", response.getJob(), "Должность пользователя должна совпадать");
            assertTrue(response.getUpdatedAt().matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"),
                    "Поле updatedAt должно соответствовать формату даты"
            );
        });
    }
}