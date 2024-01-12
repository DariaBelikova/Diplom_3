import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UserProfilePage {

    private final By profile = By.xpath("//*[@id='root']/div/main/div/nav/ul/li[1]/a");
    private final By constructorButton = By.xpath(".//p[text()='Конструктор']");

    private final By logoButton = By.xpath("//*[contains(@class, 'AppHeader_header__logo__2D0X2')]");
    private final By exitButton = By.xpath(".//button[text()='Выход']");

    private final WebDriver webDriver;

    public UserProfilePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
    @Step("Получение текста «Профиль» на странице личного кабинета")
    public String getProfileText() {
        return webDriver.findElement(profile).getText();
    }
    @Step("Тап по кнопке «Конструктор»")
    public void clickConstructorButton() {
        webDriver.findElement(constructorButton).click();
    }
    @Step("Тап по кнопке «Лого»")
    public void clicklogoButton() {
        webDriver.findElement(logoButton).click();
    }
    @Step("Тап по кнопке «Выход» на странице личного кабинета")
    public void clickExitButton() {
        webDriver.findElement(exitButton).click();
    }


}
