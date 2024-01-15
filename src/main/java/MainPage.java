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
    private final By sauceButton = By.xpath("//*[text()= 'Соусы']");
    private final By fillingButton = By.xpath("//*[text()= 'Начинки']");
    private final By ingredientsTitles = By.xpath(".//div[starts-with(@class, 'BurgerIngredients_ingredients__menuContainer')]/h2");
    private final By ingredientsButtons = By.xpath(".//section[starts-with(@class, 'BurgerIngredients_ingredients')]/div/div");

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
        waitScrolled(0);
    }

    @Step("Тап по вкладке «Соусы» на главной странице")
    public void clickSauceButton() {
        webDriver.findElement(sauceButton).click();
        waitScrolled(1);
    }

    @Step("Тап по вкладке «Начинки» на главной странице")
    public void clickFillingButton() {
        webDriver.findElement(fillingButton).click();
        waitScrolled(2);
    }


    public int getBunsLocation() {
        return Integer.valueOf(webDriver.findElements(ingredientsTitles).get(0).getLocation().getY());
    }

    public int getSousLocation() {
        return Integer.valueOf(webDriver.findElements(ingredientsTitles).get(1).getLocation().getY());
    }

    public int getFillingLocation() {
        return Integer.valueOf(webDriver.findElements(ingredientsTitles).get(2).getLocation().getY());
    }

    private void waitScrolled(int n) {
        new WebDriverWait(webDriver, Duration.ofSeconds(30))
                .until(webDriver -> {
                            return webDriver.findElements(ingredientsTitles).get(n).getLocation().getY() == 243;
                        }
                );
    }

    public int getIngredientTitleExpectedLocation() {
        return Integer.valueOf(webDriver.findElements(ingredientsButtons).get(0).getLocation().getY()
                + webDriver.findElements(ingredientsButtons).get(0).getSize().getHeight()
        );
    }

    public int getBunTitleExpectedLocation() {
        return Integer.valueOf(webDriver.findElements(ingredientsButtons).get(0).getLocation().getY()
                + webDriver.findElements(ingredientsButtons).get(0).getSize().getHeight()
        );
    }

}
