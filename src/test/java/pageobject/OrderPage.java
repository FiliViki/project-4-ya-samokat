package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.*;

public class OrderPage implements Scrollable {

    private final WebDriver driver;
    public static final String ORDER_PAGE_URL = "https://qa-scooter.praktikum-services.ru/order";

    private static final By FIRSTNAME_INPUT_FIELD = By.xpath(".//input[@placeholder='* Имя']");

    private static final By SECONDNAME_INPUT_FIELD = By.xpath(".//input[@placeholder='* Фамилия']");

    private static final By ADDRESS_INPUT_FIELD = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");

    private static final By STATION_INPUT_FIELD = By.xpath(".//input[@placeholder='* Станция метро']/parent::*/parent::*");

    private static final By STATIONS_LIST = By.xpath(".//div[@class='Order_Text__2broi']/parent::*/parent::*");

    private static final By PHONE_INPUT_FIELD = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");

    private static final By NEXT_BUTTON_ORDER_PAGE = By.xpath(".//button[text()='Далее']");

    public static final By CALENDAR_INPUT_FIELD = By.xpath(".//input[@placeholder='* Когда привезти самокат']");

    public static final By RENT_DROPDOWN_FIELD = By.xpath(".//div[@class='Dropdown-root']");

    public static final By RENT_DROPDOWN_LIST = By.xpath(".//div[@class='Dropdown-menu']/div");

    public static final By BOT_ORDER_BUTTON_ORDER_PAGE = By.xpath(".//div[@class='Order_Buttons__1xGrp']/button[text()='Заказать']");

    public static final By BUTTON_YES_CONFIRM_ORDER_WINDOW = By.xpath(".//button[text()='Да']");

    public static final By HEADER_ORDER_HAS_BEEN_PLACED = By.xpath(".//div[text()='Заказ оформлен']");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getNameInputField() {
        return driver.findElement(FIRSTNAME_INPUT_FIELD);
    }

    public WebElement getSurnameInputField() {
        return driver.findElement(SECONDNAME_INPUT_FIELD);
    }

    public WebElement getAddressInputField() {
        return driver.findElement(ADDRESS_INPUT_FIELD);
    }

    public WebElement getStationInputField() {
        return driver.findElement(STATION_INPUT_FIELD);
    }

    public WebElement getStation(String station) {
        return driver
                .findElements(STATIONS_LIST)
                .stream()
                .filter(element1 -> element1.getText().equalsIgnoreCase(station))
                .findFirst().orElse(null);
    }

    public WebElement getPhoneNumberInputField() {
        return driver.findElement(PHONE_INPUT_FIELD);
    }

    public WebElement getNextButtonOrderPage() {
        return driver.findElement(NEXT_BUTTON_ORDER_PAGE);
    }

    public WebElement getCalendarInputField() {
        return driver.findElement(CALENDAR_INPUT_FIELD);
    }

    public void setCalendarDate(int daysAfterToday) {
        ZonedDateTime plusDate = new Date()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .plusDays(Math.max(daysAfterToday, 0));
        int requiredYear = plusDate.getYear();

        String requiredMonth = plusDate
                .getMonth()
                .getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru"));
        int requiredDay = plusDate.getDayOfMonth();

        WebElement currMonthElem = driver
                .findElement(By.xpath(".//div[@class='react-datepicker__current-month']"));
        WebElement nextMonthButton = driver
                .findElement(By.xpath(".//button[@aria-label='Next Month']"));

        while (!currMonthElem.getText().equals(String.format("%s %d", requiredMonth, requiredYear))) {
            nextMonthButton.click();
        }
        driver
                .findElement(By.xpath(String.format(".//div[@class='react-datepicker__month']/div/div[not(contains(@class, '--outside-month')) and text()=%d]", requiredDay)))
                .click();
    }

    public WebElement getRentDropdownField() {
        return driver.findElement(RENT_DROPDOWN_FIELD);
    }

    public WebElement getRentalPeriod(RentalDays count) {
        return driver.findElements(RENT_DROPDOWN_LIST).get(count.getIndex());
    }

    public WebElement getBotOrderButtonOrderPage() {
        return driver.findElement(BOT_ORDER_BUTTON_ORDER_PAGE);
    }

    public WebElement getButtonYesConfirmOrderWindow() {
        return driver.findElement(BUTTON_YES_CONFIRM_ORDER_WINDOW);
    }

    public WebElement getHeaderOrderHasBeenPlaced() {
        return driver.findElement(HEADER_ORDER_HAS_BEEN_PLACED);
    }

    @Override
    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    public void firstnameField(String name) {
        getNameInputField().sendKeys(name);
    }

    public void fillSecondnameField(String surname) {
        getSurnameInputField().sendKeys(surname);
    }

    public void fillAddressField(String address) {
        getAddressInputField().sendKeys(address);
    }

    public void selectMetroStation(String metroStation) {
        getStationInputField().click();
        WebElement metroStationElement = getStation(metroStation);

        scrollToElement(metroStationElement);
        metroStationElement.click();
    }

    public void fillPhoneNumberField(String phoneNumber) {
        getPhoneNumberInputField().sendKeys(phoneNumber);
    }

    public void selectDeliveryDate(int days) {
        getCalendarInputField().click();
        setCalendarDate(days);
    }

    public void selectRentalPeriod(RentalDays days) {
        getRentDropdownField().click();
        WebElement rentalPeriodElement = getRentalPeriod(days);

        scrollToElement(rentalPeriodElement);
        rentalPeriodElement.click();
    }

    public void clickNextButton() {
        getNextButtonOrderPage().click();
    }

    public void clickBotOrderButton() {
        getBotOrderButtonOrderPage().click();
    }

    public void clickYesButton() {
        getButtonYesConfirmOrderWindow().click();
    }
}
