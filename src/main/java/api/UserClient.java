package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.User;
import models.UserCreds;

import static io.restassured.RestAssured.given;

public class UserClient {

    private static final String CREATE_USER_URL = "/api/auth/register";
    private static final String USER_LOGIN_URL = "/api/auth/login";
    private static final String AUTH_USER = "/api/auth/user";


    @Step("Создание пользователя {user}")
    public static Response create(User user) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(CREATE_USER_URL);
    }

    @Step("Авторизация пользователя {userCreds}")
    public static Response login(UserCreds userCreds) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(userCreds)
                .when()
                .post(USER_LOGIN_URL);
    }

    @Step("Удаление пользователя")
    public static Response deleteUser(String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .header("authorization", accessToken)
                .when()
                .delete(AUTH_USER);
    }
}
