package ru.mail.test;

import junit.framework.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.mail.go.PageObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SimpleTests {
    public static final String URL_GO_MAIL_RU = "http://go.mail.ru/";
    private WebDriver driver;

    @BeforeMethod
    @Parameters({"browser", "hub", "url"})
    public void setUp(String browser, String hub, String url) throws MalformedURLException {
        if (browser.toLowerCase().equals("chrome"))
            this.driver = new RemoteWebDriver(new URL(hub), DesiredCapabilities.chrome());
        else if (browser.toLowerCase().equals("firefox"))
            this.driver = new RemoteWebDriver(new URL(hub), DesiredCapabilities.firefox());
        else
            throw new NotImplementedException();
        this.driver.manage().window().maximize();
        this.driver.get(url);
    }

/*
    @BeforeMethod
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        driver.navigate().to(URL_GO_MAIL_RU);
    }
*/

    @Test
    public void testSuggestButtonTo() throws Exception {
        loadPage();

    }

    @Test
    public void testConverterKgToPounds() {
        PageObject pageObject = loadPage();
        pageObject.enterValue("10000");
        String from = pageObject.getValueButtonFrom();
        String to = pageObject.getValueButtonTo();

        Assert.assertEquals(from, "килограмм");
        Assert.assertEquals(to, "фунта");
        Assert.assertEquals(pageObject.getResult(), "22 046,22622");
    }

    @Test
    public void testChangeConvertation() throws Exception {
        PageObject pageObject = loadPage();
        pageObject.pressedButtonFrom();

        List<String> measures = pageObject.getValueMeasureListFrom();
        pageObject.pressedToValueMeasureSuggestFrom(measures.get(3));

        Assert.assertEquals(pageObject.getValueButtonFrom(), "тройская унция");
    }

    @Test
    public void testSwapConvertation() throws Exception {
        PageObject pageObject = loadPage();
        pageObject.enterValue("10000");
        pageObject.changeConvertation();

        Assert.assertEquals(pageObject.getResult(), "4 535,9237");
    }

    @Test
    public void testMeasureSelector() throws Exception {
        PageObject pageObject = loadPage();
        pageObject.pressedToMeasureSelector();

        List<String> measure = pageObject.getMeasureSelectorList();
        Assert.assertTrue(measure.size() == 11);

        pageObject.changeConvertation(measure.get(3));

        Assert.assertEquals(pageObject.getCurrentSelectedMeasure(), "скорость");
    }

    @Test
    public void testConvertation() throws Exception {
        PageObject pageObject = loadPage();
        pageObject.pressedToMeasureSelector();

        String testHour = "60";

        chooseMeasure(pageObject, 4);//time
        chooseInInputMenu(pageObject, 5); //hour
        chooseInOutputMenu(pageObject, 4); //minutes

        pageObject.enterValue(testHour);
        Assert.assertEquals(pageObject.getResult(), "3 600");
    }

    private void chooseMeasure(PageObject pageObject, int id) {
        List<String> measure = pageObject.getMeasureSelectorList();
        pageObject.changeConvertation(measure.get(id));
        pageObject.changeConvertation();
    }

    private void chooseInOutputMenu(PageObject pageObject, int id) {
        pageObject.pressedButtonTo();
        List<String> valueMeasureTo = pageObject.getValueMeasureListTo();

        pageObject.pressedToValueMeasureSuggestTo(valueMeasureTo.get(id));
    }

    private void chooseInInputMenu(PageObject pageObject, int id) {
        pageObject.pressedButtonFrom();
        List<String> valueMeasureFrom = pageObject.getValueMeasureListFrom();

        pageObject.pressedToValueMeasureSuggestFrom(valueMeasureFrom.get(id));
    }

    private PageObject loadPage() {
        PageObject pageObject = new PageObject(driver);
        pageObject.enterTextToSearcher("кг в фунты");
        pageObject.clickButtonToSearch();
        return pageObject;
    }

    @AfterMethod
    public void tearDown() throws Exception {
        driver.quit();
    }
}
