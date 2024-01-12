import api.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.User;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.hamcrest.Matchers.equalTo;

public class LoginTest {

    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    WebDriver webDriver;
    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;

        User user = new User("RonWeasley", "Ron-data@yandex.ru", "RonRonWeasley");
        UserClient userClient = new UserClient();
        Response createResponse = userClient.create(user);
        accessToken = createResponse.path("accessToken");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        webDriver = new ChromeDriver(options);
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной")
    public void checkLoginButton() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.open();
        mainPage.waitMainPage();
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.inputEmail("Ron-data@yandex.ru");
        loginPage.inputPassword("RonRonWeasley");
        loginPage.clickLogin();

        MatcherAssert.assertThat(
                "Надпись Оформить заказ на кнопке на главной странице",
                mainPage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );

    }

    @Test
    @DisplayName("Вход по кнопке «Личный кабинет»")
    public void checkLoginLKButton() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.open();
        mainPage.waitMainPage();
        mainPage.clickLoginLKButton();

        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.inputEmail("Ron-data@yandex.ru");
        loginPage.inputPassword("RonRonWeasley");
        loginPage.clickLogin();

        MatcherAssert.assertThat(
                "Надпись Оформить заказ на кнопке на главной странице",
                mainPage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );

    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void checkLoginButtonRegisterPage() {
        RegisterPage registerPage = new RegisterPage(webDriver);
        registerPage.open();
        registerPage.clickLogin();

        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.inputEmail("Ron-data@yandex.ru");
        loginPage.inputPassword("RonRonWeasley");
        loginPage.clickLogin();

        MainPage mainPage = new MainPage(webDriver);

        MatcherAssert.assertThat(
                "Надпись Оформить заказ на кнопке на главной странице",
                mainPage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );

    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void checkLoginButtonForgotPassword() {
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(webDriver);
        forgotPasswordPage.open();
        forgotPasswordPage.clickLogin();

        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.inputEmail("Ron-data@yandex.ru");
        loginPage.inputPassword("RonRonWeasley");
        loginPage.clickLogin();

        MainPage mainPage = new MainPage(webDriver);

        MatcherAssert.assertThat(
                "Надпись Оформить заказ на кнопке на главной странице",
                mainPage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );
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
