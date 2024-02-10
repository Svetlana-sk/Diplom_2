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

public class CreateOrderTest {
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
        orderClient = new OrderClient();
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Создание заказа с ингридиентами и авторизацей пользователя")
    public void createOrderWithIngredientsAndAuth() {
        orderData = new OrderData(List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa73"));
        orderClient
                .createOrderWithAuth(orderData, accessToken)
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа с ингридиентами без авторизации пользователя")
    public void createOrderWithIngredientsAndWithoutAuth() {
        orderData = new OrderData(List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa73"));
        orderClient
                .createOrderWithoutAuth(orderData)
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа без ингридиентов с авторизацей пользователя")
    public void createOrderWithoutIngredientsAndAuth() {
        orderData = new OrderData(List.of());
        orderClient
                .createOrderWithAuth(orderData, accessToken)
                .then()
                .statusCode(400)
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа без ингридиентов без авторизации пользователя")
    public void createOrderWithoutIngredientsAndWithoutAuth() {
        orderData = new OrderData(List.of());
        orderClient
                .createOrderWithoutAuth(orderData)
                .then()
                .statusCode(400)
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void createOrderWithInvalidIngredients() {
        orderData = new OrderData(List.of("fk8678er738owjqijg"));
        orderClient
                .createOrderWithAuth(orderData, accessToken)
                .then()
                .statusCode(500);
    }
}