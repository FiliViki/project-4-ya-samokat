package orderpagetests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.RentalDays;
import pageobject.MainPage;
import pageobject.OrderPage;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderScenarioTests {

    private ChromeOptions options;
    private WebDriver driver;
    private WebDriverWait webDriverWait;

    private OrderPage orderPage;
    private MainPage mainPage;

    private String firstname;
    private String secondname;
    private String address;
    private String station;
    private String phone;
    private int calendarDaysAfterToday;
    private RentalDays rentalDaysCount;

    public OrderScenarioTests(
            String firstname,
            String secondname,
            String address,
            String station,
            String phone,
            int calendarDaysAfterToday,
            RentalDays rentalDaysCount
    ) {
        this.firstname = firstname;
        this.secondname = secondname;
        this.address = address;
        this.station = station;
        this.phone = phone;
        this.calendarDaysAfterToday = calendarDaysAfterToday;
        this.rentalDaysCount = rentalDaysCount;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {"Алёша", "Попович", "ул. Пушкина, д. Колотушкина", "Лихоборы", "88005553535", 99, RentalDays.SEVEN},
                {"Йеллоу", "Кард", "Оушен Авеню", "Деловой центр", "+79999999999", 2003, RentalDays.ONE},
        };
    }

    @Before
    public void setUp() {
        options = new ChromeOptions().addArguments("--disable-cookies"); // Не уверен что работает, но индус на ютубе сказал что работает. Не знаю как проверить.
        driver = new ChromeDriver(options);
        driver.get(MainPage.MAIN_PAGE_URL);
        mainPage = new MainPage(driver);
        orderPage = new OrderPage(driver);
        webDriverWait = new WebDriverWait(driver, 3);
    }

    @Test
    public void clickShouldCorrectOrder() {
        mainPage.clickTopOrderButton();
        webDriverWait.until(ExpectedConditions.urlToBe(OrderPage.ORDER_PAGE_URL));
        orderPage.firstnameField(firstname);
        orderPage.fillSecondnameField(secondname);
        orderPage.fillAddressField(address);
        orderPage.selectMetroStation(metroStation);
        orderPage.fillPhoneNumberField(phoneNumber);
        orderPage.clickNextButton();
        orderPage.selectDeliveryDate(calendarDaysAfterToday);
        orderPage.selectRentalPeriod(rentalDaysCount);
        orderPage.clickBotOrderButton();
        orderPage.clickYesButton();

        assertTrue(orderPage.getHeaderOrderHasBeenPlaced().isDisplayed());
    }

    @Test
    public void shouldMakeOrderViaMainPageBotOrderButton() {
        mainPage.scrollToElement(mainPage.getBotOrderButton());
        mainPage.clickBotOrderButton();
        webDriverWait.until(ExpectedConditions.urlToBe(OrderPage.ORDER_PAGE_URL));
        orderPage.firstnameField(firstname);
        orderPage.fillSecondnameField(secondname);
        orderPage.fillAddressField(address);
        orderPage.selectMetroStation(station);
        orderPage.fillPhoneNumberField(phone);
        orderPage.clickNextButton();
        orderPage.selectDeliveryDate(calendarDaysAfterToday);
        orderPage.selectRentalPeriod(rentalDaysCount);
        orderPage.clickBotOrderButton();
        orderPage.clickYesButton();
        assertTrue(orderPage.getHeaderOrderHasBeenPlaced().isDisplayed());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
