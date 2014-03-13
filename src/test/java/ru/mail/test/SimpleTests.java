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
import ru.mail.go.Converter;
import ru.mail.go.Searcher;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SimpleTests {
    public static final String URL_GO_MAIL_RU = "http://go.mail.ru/";
    private WebDriver driver;
    private Converter converter;

/*    @BeforeMethod
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

        loadPage();
        converter = new Converter(driver);
    }*/

    private void loadPage() {
        Searcher searcher = new Searcher(driver);
        searcher.enterText("кг в фунты");
        searcher.go();
    }

    @BeforeMethod
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        driver.navigate().to(URL_GO_MAIL_RU);
        loadPage();
        converter = new Converter(driver);
    }


    @Test
    public void testConverterKgToPounds() {
        converter.enterValue("10000");
        String from = converter.getMeasureFrom();
        String to = converter.getMeasureTo();

        Assert.assertEquals(from, "килограмм");
        Assert.assertEquals(to, "фунта");
        Assert.assertEquals(converter.getResultValue(), "22 046,22622");
    }

    @Test
    public void testChangeConvertation() throws Exception {
        converter.pressedButtonFrom();

        List<String> measures = converter.getValueMeasureListFrom();
        converter.pressedToValueMeasureSuggestFrom(measures.get(3));

        Assert.assertEquals(converter.getMeasureFrom(), "тройская унция");
    }

    @Test
    public void testSwapConvertation() throws Exception {
        converter.enterValue("10000");
        converter.changeConvertation();

        Assert.assertEquals(converter.getResultValue(), "4 535,9237");
    }

    @Test
    public void testMeasureSelector() throws Exception {
        converter.pressedToMeasureSelector();

        List<String> measure = converter.getMeasureSelectorList();
        Assert.assertTrue(measure.size() == 11);

        converter.changeConvertation(measure.get(3));

        Assert.assertEquals(converter.getCurrentSelectedMeasure(), "скорость");
    }

    @Test
    public void testConvertation() throws Exception {
        converter.pressedToMeasureSelector();

        String testHour = "60";

        chooseMeasure(converter, 4);//time
        chooseInInputMenu(converter, 5); //hour
        chooseInOutputMenu(converter, 4); //minutes

        converter.enterValue(testHour);
        Assert.assertEquals(converter.getResultValue(), "3 600");
    }

    private void chooseMeasure(Converter converter, int id) {
        List<String> measure = converter.getMeasureSelectorList();
        converter.changeConvertation(measure.get(id));
        converter.changeConvertation();
    }

    private void chooseInOutputMenu(Converter converter, int id) {
        converter.pressedButtonTo();
        List<String> valueMeasureTo = converter.getValueMeasureListTo();

        converter.pressedToValueMeasureSuggestTo(valueMeasureTo.get(id));
    }

    private void chooseInInputMenu(Converter converter, int id) {
        converter.pressedButtonFrom();
        List<String> valueMeasureFrom = converter.getValueMeasureListFrom();

        converter.pressedToValueMeasureSuggestFrom(valueMeasureFrom.get(id));
    }

    @AfterMethod
    public void tearDown() throws Exception {
        driver.quit();
    }
}
