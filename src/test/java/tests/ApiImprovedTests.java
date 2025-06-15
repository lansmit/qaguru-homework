package tests;

import models.registration.UserRegistrationModel;
import models.user_changes.UserBodyModel;
import models.user_changes.UserCreationResponseModel;
import models.user_changes.UserResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static specs.BaseSpec.RequestSpec;
import static specs.BaseSpec.responseSpec;

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
            assertThat(response.getName())
                    .as("Имя пользователя должно совпадать")
                    .isEqualTo("Skrillex");

            assertThat(response.getJob())
                    .as("Должность пользователя должна совпадать")
                    .isEqualTo("Producer");

            assertThat(response.getUpdatedAt())
                    .as("Поле updatedAt должно соответствовать формату даты")
                    .matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z");
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
                        .spec(responseSpec(201))
                        .body("name", equalTo("Skrillex"))
                        .body("password", nullValue()));
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
            assertThat(response.getName())
                    .as("Имя пользователя должно совпадать")
                    .isEqualTo("Skrillex");

            assertThat(response.getJob())
                    .as("Должность пользователя должна совпадать")
                    .isEqualTo("Producer");

            assertThat(response.getUpdatedAt())
                    .as("Поле updatedAt должно соответствовать формату даты")
                    .matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z");
        });
    }
}