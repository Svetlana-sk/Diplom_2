package ru.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.client.UserClient;
import ru.praktikum.client.UserData;

import static org.hamcrest.CoreMatchers.equalTo;

public class UpdateUserTest {

    private UserClient userClient;
    private UserData userData;
    private Response response;
    private String accessToken;

    @Before
    public void setUp() {
        userData = new UserData(RandomStringUtils.randomAlphabetic(6, 10) + "@ya.ru", RandomStringUtils.randomAlphabetic(6, 10), RandomStringUtils.randomAlphabetic(6, 10));
        userClient = new UserClient();
        response = userClient.createUser(userData);
        accessToken = response.then().extract().body().path("accessToken");
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Изменение данных пользователя с авторизацией")
    public void updateUserDataWithAuth() {
        userClient
                .updateUserDataWithAuth(userData, accessToken)
                .then()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    public void updateUserDataWithoutAuth() {
        userClient
                .updateUserDataWithoutAuth(userData)
                .then()
                .assertThat()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
    }

}