import api.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static models.UserCreds.fromUser;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;


public class RegisterTest {

    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    WebDriver webDriver;
    private String accessToken;

    @Before
    public void setUp() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        webDriver = new ChromeDriver(options);
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

    }

    @Test
    @DisplayName("Проверка успешной регистрации")
    public void checkRegister() {


        RegisterPage registerPage = new RegisterPage(webDriver);
        registerPage.open();

        registerPage.inputName("Neville");
        registerPage.inputEmail("neville@yandex.ru");
        registerPage.inputPassword("neville");

        registerPage.clickRegister();

        RestAssured.baseURI = BASE_URL;

        User user = new User("Neville", "neville@yandex.ru", "neville");
        Response loginResponse = UserClient.login(fromUser(user));
        accessToken = loginResponse.path("accessToken");

        assertEquals("Неверный статус код", SC_OK, loginResponse.statusCode());

    }

    @Test
    @DisplayName("Проверка некорректного пароля. Минимальный пароль — шесть символов")
    public void checkRegisterWithWrongPassword() {
        RegisterPage registerPage = new RegisterPage(webDriver);
        registerPage.open();

        registerPage.inputName("Neville");
        registerPage.inputEmail("neville@yandex.ru");
        registerPage.inputPassword("ron");

        registerPage.clickRegister();

        Assert.assertTrue(registerPage.loginError());


    }

    @After
    @DisplayName("Удаление пользователя")
    public void tearDown() {
        webDriver.quit();

        if (accessToken != null) {
            UserClient.deleteUser(accessToken).then().assertThat().body("success", equalTo(true))
                    .and()
                    .body("message", equalTo("User successfully removed"))
                    .and()
                    .statusCode(SC_ACCEPTED);
        }
    }
}
