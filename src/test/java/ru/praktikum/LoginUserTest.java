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

public class LoginUserTest {

    String accessToken;
    private UserClient userClient;
    private UserData userData;
    private Response response;

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
    @DisplayName("Логин под существующим пользователем")
    public void loginUser() {
        userClient
                .loginUser(userData)
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Логин с неверным логином и паролем")
    public void loginWithInvalidData() {
        userData = new UserData(RandomStringUtils.randomAlphabetic(6, 10) + "@ya.ru", RandomStringUtils.randomAlphabetic(6, 10), RandomStringUtils.randomAlphabetic(6, 10));
        userClient
                .loginUser(userData)
                .then()
                .statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
    }

}