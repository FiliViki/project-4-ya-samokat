package mainpagetests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.MainPage;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class QuestionsListTest {

    private ChromeOptions options;
    private WebDriver driver;
    private WebDriverWait webDriverWait;

    private MainPage mainPage;

    private final String question;
    private final String answer;

    public QuestionsListTest(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    @Before
    public void setUp() {
        options = new ChromeOptions().addArguments("--disable-cookies");
        driver = new ChromeDriver(options);

        driver.get(MainPage.MAIN_PAGE_URL);

        mainPage = new MainPage(driver);
        webDriverWait = new WebDriverWait(driver, 5);
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {"Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {"Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {"Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {"Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {"Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {"Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {"Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {"Я жизу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области."},
        };
    }

    @Test
    public void answerShouldCompareQuestion() {
        WebElement questionElement = mainPage.getQuestionElementWithText(question);

        mainPage.scrollToElement(questionElement);
        questionElement.click();

        webDriverWait.until(ExpectedConditions.attributeToBe(questionElement, "aria-expanded", "true"));
        WebElement answerElement = mainPage.getExpandedAnswer();
        webDriverWait.until(ExpectedConditions.visibilityOf(answerElement));

        assertEquals(
                "Ответ и вопрос не совпадают",
                answer,
                answerElement.getText()
        );
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
