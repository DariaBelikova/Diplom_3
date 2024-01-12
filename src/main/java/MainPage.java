import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {

    private static final String URL = "https://stellarburgers.nomoreparties.site/";

    private final By loginButton = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By basketButton = By.xpath(".//div[starts-with(@class,'BurgerConstructor_basket__container')]/button");
    private final By loginLKButton = By.xpath(".//p[text()='Личный Кабинет']");
    private final By bunsButton = By.xpath("//*[text()= 'Булки']");
    private final By bunsSection = By.xpath("//*[@class = 'text text_type_main-medium mb-6 mt-10' and contains(./text(), 'Булки')]");
    private final By sauceButton = By.xpath("//*[text()= 'Соусы']");
    private final By sauceSection = By.xpath("//*[@class = 'text text_type_main-medium mb-6 mt-10' and contains(./text(), 'Соусы')]");
    private final By fillingButton = By.xpath("//*[text()= 'Начинки']");
    private final By fillingSection = By.xpath("//*[@class = 'text text_type_main-medium mb-6 mt-10' and contains(./text(), 'Начинки')]");


    private final WebDriver webDriver;

    public MainPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
    @Step("Открытие главной страницы")
    public void open() {
        webDriver.get(URL);
    }
    @Step("Тап по кнопке «Войти в аккаунт» на главной странице")
    public void clickLoginButton() {
        webDriver.findElement(loginButton).click();
    }
    @Step("Ожидание загрузки страницы")
    public void waitMainPage() {
        new WebDriverWait(webDriver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(loginButton));
    }
    @Step("Получение текста на кнопке «Корзина» на главной странице")
    public String getBasketButtonText() {
        return webDriver.findElement(basketButton).getText();
    }
    @Step("Тап по кнопке «Личный кабинет» на главной станице")
    public void clickLoginLKButton() {
        webDriver.findElement(loginLKButton).click();
    }
    @Step("Тап по вкладке «Булки» на главной странице")
    public void clickBunsButton() {
        webDriver.findElement(bunsButton).click();
    }
    @Step("Тап по вкладке «Соусы» на главной странице")
    public void clickSauceButton() {
        webDriver.findElement(sauceButton).click();
    }
    @Step("Тап по вкладке «Начинки» на главной странице")
    public void clickFillingButton() {
        webDriver.findElement(fillingButton).click();
    }
    @Step("Получение текста заголовка «Булки» на главной странице")
    public String getSauceSectionText() {
        return webDriver.findElement(sauceSection).getText();
    }
    @Step("Получение текста заголовка «Соучы» на главной странице")
    public String getBunsSectionText() {
        return webDriver.findElement(bunsSection).getText();
    }
    @Step("Получение текста заголовка «Начинки» на главной странице")
    public String getFillingSectionText() {
        return webDriver.findElement(fillingSection).getText();
    }


}
