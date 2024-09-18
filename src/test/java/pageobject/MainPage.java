package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage implements Scrollable {

    private final WebDriver driver;
    public static final String MAIN_PAGE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final By FAQ_EXPANDED_ANSWER = By.xpath(".//div[@class='accordion__panel' and not(@hidden)]");
    private static final By TOP_ORDER_BUTTON = By.xpath(".//button[@class='Button_Button__ra12g']");
    private static final By BOT_ORDER_BUTTON = By.xpath(".//div[@class='Home_FinishButton__1_cWm']/button");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getQuestionElementWithText(String questionText) {
        By currentQuestion = By.xpath(String.format(".//div[@aria-expanded and text()='%s']", questionText));
        return driver.findElement(currentQuestion);
    }

    public WebElement getExpandedAnswer() {
        return driver.findElement(FAQ_EXPANDED_ANSWER);
    }

    public WebElement getTopOrderButton() {
        return driver.findElement(TOP_ORDER_BUTTON);
    }

    public WebElement getBotOrderButton() {
        return driver.findElement(BOT_ORDER_BUTTON);
    }

    public void clickTopOrderButton() {
        getTopOrderButton().click();
    }

    public void clickBotOrderButton() {
        getBotOrderButton().click();
    }

    @Override
    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }
}
