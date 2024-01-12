import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage {

    public static final String URL = "https://stellarburgers.nomoreparties.site/forgot-password";
    private final WebDriver webDriver;
    private final By loginButton = By.xpath(".//a[text()='Войти']");

    public ForgotPasswordPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
    @Step("Открытие страницы восстановления пароля")
    public void open() {
        webDriver.get(URL);
    }
    @Step("Тап по кнопке «Войти» на странице восстановления пароля")
    public void clickLogin() {
        webDriver.findElement(loginButton).click();
    }
}
