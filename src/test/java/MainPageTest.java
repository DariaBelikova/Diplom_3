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

public class MainPageTest {

    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    WebDriver webDriver;
    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        User user = new User("PadmeAmidala", "Padme-data@yandex.ru", "padmeAmidala");
        Response createResponse = UserClient.create(user);
        accessToken = createResponse.path("accessToken");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        webDriver = new ChromeDriver(options);
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        RegisterPage registerPage = new RegisterPage(webDriver);
        registerPage.open();
        registerPage.clickLogin();

        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.inputEmail("Padme-data@yandex.ru");
        loginPage.inputPassword("padmeAmidala");
        loginPage.clickLogin();

    }

    @Test
    @DisplayName("Переход к разделу «Соусы»")
    public void checkLinkToSauce() {
        MainPage mainPage = new MainPage(webDriver);

        int expLocation = mainPage.getIngredientTitleExpectedLocation();

        mainPage.clickSauceButton();

        MatcherAssert.assertThat(
                "Окно не проскролилось до раздела Соусы",
                mainPage.getSousLocation(),
                equalTo(expLocation)
        );
    }

    @Test
    @DisplayName("Переход к разделу «Булки»")
    public void checkLinkToBuns() {
        MainPage mainPage = new MainPage(webDriver);

        int expLocation = mainPage.getIngredientTitleExpectedLocation();

        mainPage.clickSauceButton();

        mainPage.clickBunsButton();

        MatcherAssert.assertThat(
                "Окно не проскролилось до раздела Булки",
                mainPage.getBunsLocation(),
                equalTo(expLocation)
        );


    }

    @Test
    @DisplayName("Переход к разделу «Начинки»")
    public void checkLinkToFilling() {
        MainPage mainPage = new MainPage(webDriver);

        int expLocation = mainPage.getIngredientTitleExpectedLocation();

        mainPage.clickFillingButton();

        MatcherAssert.assertThat(
                "Окно не проскролилось до раздела Ингридиенты",
                mainPage.getFillingLocation(),
                equalTo(expLocation)
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
