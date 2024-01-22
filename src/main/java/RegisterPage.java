import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class RegisterPage {

    public static String PAGE_URL = "https://stellarburgers.nomoreparties.site/register";

    private final By nameField = By.xpath(".//label[text()='Имя']/../input");
    private final By emailField = By.xpath(".//label[text()='Email']/../input");
    private final By passwordField = By.xpath(".//label[text()='Пароль']/../input");
    private final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private final By loginButton = By.xpath(".//a[text()='Войти']");
    private final By loginError = By.xpath(".//p[text()='Некорректный пароль']");

    private final WebDriver webDriver;

    public RegisterPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Step("Заполнение поля «Email» на странице регистрации")
    public void inputEmail(String email) {
        webDriver.findElement(emailField).sendKeys(email);
    }

    @Step("Заполнение поля «Имя» на странице регистрации")
    public void inputName(String name) {
        webDriver.findElement(nameField).sendKeys(name);
    }

    @Step("Заполнение поля «Пароль» на странице регистрации")
    public void inputPassword(String password) {
        webDriver.findElement(passwordField).sendKeys(password);
    }

    @Step("Тап по кнопке «Зарегистрироваться» на странице регистрации")
    public void clickRegister() {
        webDriver.findElement(registerButton).click();
    }

    @Step("Тап по кнопке «Войти» на странице регистрации")
    public void clickLogin() {
        webDriver.findElement(loginButton).click();
    }

    @Step("Открытие страницы Регистрации")
    public void open() {
        webDriver.get(PAGE_URL);
    }

    @Step("Текст «Некорректный пароль» на странице регистрации")
    public boolean loginError() {
        return webDriver.findElement(loginError).isDisplayed();
    }

}
