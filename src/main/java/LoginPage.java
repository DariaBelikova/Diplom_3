import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private static final String URL = "https://stellarburgers.nomoreparties.site/login";
    private final WebDriver webDriver;
    private final By emailField = By.xpath(".//input[@type='text']");
    private final By passwordField = By.xpath(".//input[@type='password']");
    private final By loginButton = By.xpath(".//button[text()='Войти']");

    public LoginPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Step("Открытие страницы /login")
    public void open() {
        webDriver.get(URL);
    }

    @Step("Ввод в поле «Email»")
    public void inputEmail(String email) {
        webDriver.findElement(emailField).sendKeys(email);
    }

    @Step("Ввод в поле «Пароль»")
    public void inputPassword(String password) {
        webDriver.findElement(passwordField).sendKeys(password);
    }

    @Step("Тап на кнопке «Войти» на странице /login")
    public void clickLogin() {
        webDriver.findElement(loginButton).click();
    }

    @Step("Ожидание загрузки на странице /login")
    public void waitLoginPage() {
        new WebDriverWait(webDriver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(emailField));
    }


}
