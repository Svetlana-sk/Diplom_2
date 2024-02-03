package ru.praktikum.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.config.RestConfig;

import static io.restassured.RestAssured.given;

public class UserClient {

    @Step("Создание пользователя")
    public Response createUser(UserData userData) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(userData)
                .when()
                .post(RestConfig.BASE_URI + RestConfig.REGISTER_PATH);
    }

    @Step("Удаление пользователя")
    public void deleteUser(String accessToken) {
        given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .delete(RestConfig.BASE_URI + RestConfig.USER_PATH + accessToken)
                .then();
    }

    @Step("Логин пользователя c токеном")
    public Response loginUser(UserData userData) {
        return given()
                .header("Content-type", "application/json")
                .body(userData)
                .when()
                .post(RestConfig.BASE_URI + RestConfig.LOGIN_PATH);
    }


    @Step("Изменение данных пользователя с авторизацией")
    public Response updateUserDataWithAuth(UserData UserData, String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .and()
                .when()
                .body(UserData)
                .when()
                .patch(RestConfig.BASE_URI + RestConfig.USER_PATH);
    }

    @Step("Изменение данных пользователя без авторизации")
    public Response updateUserDataWithoutAuth(UserData UserData) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .body(UserData)
                .when()
                .patch(RestConfig.BASE_URI + RestConfig.USER_PATH);
    }
}