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
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class UserProfileTest {

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

        RegisterPage registerPage = new RegisterPage(webDriver);
        registerPage.open();
        registerPage.clickLogin();

        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.inputEmail("Ron-data@yandex.ru");
        loginPage.inputPassword("RonRonWeasley");
        loginPage.clickLogin();

    }

    @Test
    @DisplayName("Переход по клику на «Личный кабинет»")
    public void checkLKButton() {
        MainPage mainPage = new MainPage(webDriver);

        mainPage.clickLoginLKButton();

        UserProfilePage userProfilePage = new UserProfilePage(webDriver);

        MatcherAssert.assertThat(
                "Ожидается надпись «Профиль» на кнопке в корзине",
                userProfilePage.getProfileText(),
                equalTo("Профиль")
        );

    }

    @Test
    @DisplayName("Переход по клику на «Конструктор»")
    public void checkLinkToConstructor() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.clickLoginLKButton();

        UserProfilePage userProfilePage = new UserProfilePage(webDriver);
        userProfilePage.clickConstructorButton();

        MatcherAssert.assertThat(
                "Надпись Оформить заказ на кнопке на главной странице",
                mainPage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );

    }

    @Test
    @DisplayName("Переход по клику на логотип Stellar Burgers")
    public void checkLinkToLogo() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.clickLoginLKButton();

        UserProfilePage userProfilePage = new UserProfilePage(webDriver);
        userProfilePage.clicklogoButton();

        MatcherAssert.assertThat(
                "Надпись Оформить заказ на кнопке на главной странице",
                mainPage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );

    }

    @Test
    @DisplayName("Выход по кнопке «Выйти» в личном кабинете")
    public void checkExitButton() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.clickLoginLKButton();

        UserProfilePage userProfilePage = new UserProfilePage(webDriver);
        userProfilePage.clickExitButton();
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.waitLoginPage();

        MatcherAssert.assertThat(
                "Некорректный URL страницы Авторизации",
                webDriver.getCurrentUrl(),
                containsString("/login")
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
