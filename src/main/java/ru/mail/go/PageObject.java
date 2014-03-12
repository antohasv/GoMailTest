package ru.mail.go;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PageObject {
    public static final String QUERY_ID = "q";
    public static final String INPUT_VALUE_ID = "ival";
    public static final String OUTPUT_VALUE_ID = "oval";
    public static final String CHANGE_CONVERTATION_ID = "change_conv";
    public static final String INPUT_CODE_ID = "icode";
    public static final String OUTPUT_CODE_ID = "ocode";
    public static final String TITLE_SPAN_ID = "smack-converter__title-span";
    public static final String LIST_BLOCK_ID = "smack-converter_list-block";
    public static final String CONVERTER_BLOCK = "smack-converter_DBlock";
    public static final String SPAN = "span";
    public static final String DIVIDER = "\n";
    public static final String SELECT_SECOND = "select_second";
    public static final String SELECT_FIRST = "select_first";

    private WebDriver webDriver;

    public PageObject(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void enterTextToSearcher(String query) {
        webDriver.findElement(By.id(QUERY_ID)).sendKeys(query);
    }

    public void clickButtonToSearch() {
        webDriver.findElement(By.cssSelector(".go-form__submit")).click();
    }

    public void enterValue(String value) {
        cleanInputValue();
        findDynamicElement(By.id(INPUT_VALUE_ID), 30);
        webDriver.findElement(By.id(INPUT_VALUE_ID)).sendKeys(value);
    }

    public void cleanInputValue() {
        webDriver.findElement(By.id(INPUT_VALUE_ID)).clear();
    }

    public String getResult() {
        return webDriver.findElement(By.id(OUTPUT_VALUE_ID)).getText();
    }

    public WebElement findDynamicElement(By by, int timeOut) {
        WebDriverWait wait = new WebDriverWait(webDriver, timeOut);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public String getValueButtonFrom() {
        return webDriver.findElement(By.id(INPUT_CODE_ID)).getText();
    }

    public String getValueButtonTo() {
        return webDriver.findElement(By.id(OUTPUT_CODE_ID)).getText();
    }

    public String getCurrentSelectedMeasure() {
        return webDriver.findElement(By.className(TITLE_SPAN_ID)).getText();
    }

    public void pressedButtonFrom() {
        webDriver.findElement(By.id(INPUT_CODE_ID)).click();
    }

    public void pressedButtonTo() {
        webDriver.findElement(By.id(OUTPUT_CODE_ID)).click();
    }

    public void pressedToMeasureSelector() {
        By by = By.className(TITLE_SPAN_ID);
        findDynamicElement(by, 30);
        webDriver.findElement(by).click();
    }

    public List<String> getMeasureSelectorList() {
        String measures = webDriver.findElement(By.className(CONVERTER_BLOCK)).getText();
        return Arrays.asList(measures.split(DIVIDER));
    }

    public List<String> getValueMeasureListFrom() {
        String measures = webDriver.findElement(By.id(SELECT_FIRST))
                            .findElement(By.className(LIST_BLOCK_ID)).getText();
        return Arrays.asList(measures.split(DIVIDER));
    }

    public List<String> getValueMeasureListTo() {
        String measures = webDriver.findElement(By.id(SELECT_SECOND))
                            .findElement(By.className(LIST_BLOCK_ID)).getText();
        return Arrays.asList(measures.split(DIVIDER));
    }

     public void pressedToValueMeasureSuggestFrom(String measure) {
        List<WebElement> webElements = webDriver.findElement(By.id(SELECT_FIRST))
                .findElement(By.className(LIST_BLOCK_ID)).findElements(By.tagName(SPAN));
         findElement(webElements, measure).click();
    }

    public void pressedToValueMeasureSuggestTo(String measure) {
        List<WebElement> webElements = webDriver.findElement(By.id(SELECT_SECOND))
                .findElement(By.className(LIST_BLOCK_ID)).findElements(By.tagName(SPAN));
        findElement(webElements, measure).click();
    }

    private WebElement findElement(List<WebElement> webElements, String searchElement) {
        for (WebElement webElement : webElements) {
            if (searchElement.equals(webElement.getText())) {
                return webElement;
            }
        }
        return null;
    }

    public void changeConvertation() {
        webDriver.findElement(By.id(CHANGE_CONVERTATION_ID)).click();
    }

    public void changeConvertation(String measure) {
        List<WebElement> webElements = webDriver.findElement(By.className(CONVERTER_BLOCK)).findElements(By.tagName(SPAN));
        findElement(webElements, measure).click();
    }
}
