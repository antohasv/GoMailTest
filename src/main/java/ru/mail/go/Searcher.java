package ru.mail.go;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Searcher {
    public static final String QUERY_ID = "q";
    public static final String GO_FORM_SUBMIT = ".go-form__submit";
    private WebDriver webDriver;

    public Searcher(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void enterText(String query) {
        webDriver.findElement(By.id(QUERY_ID)).sendKeys(query);
    }

    public void go() {
        webDriver.findElement(By.cssSelector(GO_FORM_SUBMIT)).click();
    }
}
