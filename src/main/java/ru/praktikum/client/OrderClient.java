package ru.praktikum.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.config.RestConfig;

import static io.restassured.RestAssured.given;

public class OrderClient {


    @Step("Создание заказа с авторизацией")
    public Response createOrderWithAuth(OrderData orderData, String accessToken) {
        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", accessToken)
                .body(orderData)
                .post(RestConfig.BASE_URI + RestConfig.ORDERS);
    }

    @Step("Создание заказа без авторизациии")
    public Response createOrderWithoutAuth(OrderData orderData) {
        return given()
                .header("Content-Type", "application/json")
                .and()
                .when()
                .body(orderData)
                .when()
                .post(RestConfig.BASE_URI + RestConfig.ORDERS);
    }

    @Step("Получение списка заказов пользователя с авторизацией")
    public Response getUserOrdersWithAuth(String accessToken) {
        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", accessToken)
                .get(RestConfig.BASE_URI + RestConfig.ORDERS);
    }

    @Step("Получение списка заказов пользователя с авторизацией")
    public Response getUserOrdersWithoutAuth() {
        return given()
                .header("Content-Type", "application/json")
                .get(RestConfig.BASE_URI + RestConfig.ORDERS);
    }
}