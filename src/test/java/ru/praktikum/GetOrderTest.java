package ru.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.client.OrderClient;
import ru.praktikum.client.OrderData;
import ru.praktikum.client.UserClient;
import ru.praktikum.client.UserData;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderTest {

    String accessToken;
    private OrderData orderData;
    private OrderClient orderClient;
    private UserData userData;
    private UserClient userClient;
    private Response response;

    @Before
    public void setUp() {
        userData = new UserData(RandomStringUtils.randomAlphabetic(6, 10) + "@ya.ru", RandomStringUtils.randomAlphabetic(6, 10), RandomStringUtils.randomAlphabetic(6, 10));
        userClient = new UserClient();
        response = userClient.createUser(userData);
        accessToken = response.then().extract().body().path("accessToken");
        orderData = new OrderData(List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa76"));
        orderClient = new OrderClient();
        orderClient.createOrderWithAuth(orderData, accessToken);
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Получение заказов авторизованного пользователя")
    public void getUserOrdersWithAuth() {
        orderClient
                .getUserOrdersWithAuth(accessToken)
                .then()
                .statusCode(200)
                .body("orders", notNullValue());
    }

    @Test
    @DisplayName("Получение заказов неавторизованного пользователя")
    public void getUserOrdersWithoutAuth() {
        orderClient
                .getUserOrdersWithoutAuth()
                .then()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
    }

}