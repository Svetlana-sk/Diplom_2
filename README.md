
Проект на Java с использованием REST Assured и Allure
Этот проект представляет собой пример использования библиотек REST Assured и Allure для написания и тестирования RESTful API запросов. Проект включает в себя несколько тестов, использующих JUnit и Hamcrest для проверки функциональности API.

Зависимости
JUnit 4.13.1
Jackson Databind 2.16.1
REST Assured 5.3.2
Allure JUnit4 2.15.0
Hamcrest All 1.3
Allure REST Assured 2.15.0

Структура проекта
src/main/java: Классы клиентов (UserClient, OrderClient) и конфигурации REST (RestConfig).
src/test/java: Тестовые классы (CreateUserTest, CreateOrderTest, GetUserTest, UpdateUserTest).
target/allure-results: Результаты выполнения тестов для генерации отчетов Allure.

Описание тестов
CreateUserTest: Тесты для создания новых пользователей, включая проверку уникальности логина и обязательных полей.
CreateOrderTest: Тесты для создания заказов с авторизацией и без, с использованием различных данных.
GetUserTest: Тесты для получения информации о пользователе с авторизацией и без.
UpdateUserTest: Тесты для обновления данных пользователя с авторизацией и без.
 
