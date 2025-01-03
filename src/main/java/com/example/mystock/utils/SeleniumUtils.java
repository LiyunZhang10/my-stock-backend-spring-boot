package com.example.mystock.utils;

import com.example.mystock.entity.GoldData;
import com.example.mystock.entity.MsData;
import com.example.mystock.entity.UsdchnData;
import com.example.mystock.entity.SgdcnycData;
import com.example.mystock.mapper.SgdcnycDataMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.PageLoadStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.logging.Logger;
import java.util.logging.Level;

@Component
public class SeleniumUtils {

    private static final Logger logger = Logger.getLogger(SeleniumUtils.class.getName());
    private static final String GOLD_URL = "http://quote.eastmoney.com/globalfuture/GC00Y.html";
    private static final String MS_URL = "https://quote.eastmoney.com/us/MSFT.html";
    private static final String USDCNH_URL = "http://quote.eastmoney.com/forex/USDCNH.html";
    private static final String SGDCNYC_URL = "http://quote.eastmoney.com/cnyrate/SGDCNYC.html";
    private static final String SELENIUM_GRID_URL = "http://localhost:4444/wd/hub";

    private final SgdcnycDataMapper sgdcnycDataMapper;

    @Autowired
    public SeleniumUtils(SgdcnycDataMapper sgdcnycDataMapper) {
        this.sgdcnycDataMapper = sgdcnycDataMapper;
    }

    public static GoldData getGoldData() {
        return fetchData(GOLD_URL, "div.zxj span span", "body > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(9) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > span:nth-child(1) > span:nth-child(1) > span:nth-child(1)", GoldData.class);
    }

    public static MsData getMsData() {
        return fetchData(MS_URL, "div.zxj span span", "div[class=\"quote3l uk_brief\"] span:nth-child(2) span:nth-child(1)", MsData.class);
    }

    public static UsdchnData getUsdchnData() {
        return fetchData(USDCNH_URL, "#app div[style=\"background-color:#fff\"] .container .gi_quote.self_clearfix .gi_quote_l.quote_quotenums .zxj span span", "#app div[style=\"background-color:#fff\"] .container .gi_quote.self_clearfix .gi_quote_l.quote_quotenums .zd span:nth-child(2) span", UsdchnData.class);
    }

    public void fetchAndSaveSgdcnycData() {
        WebDriver driver = null;
        try {
            driver = createWebDriver();
            driver.get(SGDCNYC_URL);

            WebElement medianPriceElement = driver.findElement(By.cssSelector("li:nth-child(1) span:nth-child(2) span:nth-child(1)"));
            WebElement changeAmountElement = driver.findElement(By.cssSelector("li:nth-child(3) span:nth-child(2) span:nth-child(1)"));
            WebElement changeAmplitudeElement = driver.findElement(By.cssSelector("li:nth-child(4) span:nth-child(2) span:nth-child(1)"));

            SgdcnycData sgdcnycData = new SgdcnycData();
            sgdcnycData.setMedianPrice(medianPriceElement.getText());
            sgdcnycData.setChangeAmount(changeAmountElement.getText());
            sgdcnycData.setChangeAmplitude(changeAmplitudeElement.getText());
            sgdcnycData.setTimestamp(LocalDateTime.now());

            sgdcnycDataMapper.insert(sgdcnycData); // 使用 MyBatis Plus 插入数据
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching data from " + SGDCNYC_URL, e);
        } finally {
            quitDriver(driver);
        }
    }

    private static <T> T fetchData(String url, String priceSelector, String changeRateSelector, Class<T> clazz) {
        WebDriver driver = null;
        try {
            driver = createWebDriver();
            driver.get(url);

            WebElement priceElement = driver.findElement(By.cssSelector(priceSelector));
            WebElement changeRateElement = driver.findElement(By.cssSelector(changeRateSelector));

            if (priceElement != null && changeRateElement != null) {
                Double price = Double.parseDouble(priceElement.getText());
                Double changeRate = Double.parseDouble(changeRateElement.getText().replace("%", ""));

                return createDataObject(clazz, price, changeRate);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching data from " + url, e);
        } finally {
            quitDriver(driver);
        }
        return null;
    }

    private static WebDriver createWebDriver() throws Exception {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        WebDriver driver = new RemoteWebDriver(new URL(SELENIUM_GRID_URL), options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        return driver;
    }

    private static void quitDriver(WebDriver driver) {
        if (driver != null) {
            driver.quit();
        }
    }

    private static <T> T createDataObject(Class<T> clazz, Double price, Double changeRate) {
        if (clazz.equals(GoldData.class)) {
            GoldData goldData = new GoldData();
            goldData.setPrice(price);
            goldData.setChangeRate(changeRate);
            goldData.setTimestamp(LocalDateTime.now());
            return clazz.cast(goldData);
        } else if (clazz.equals(UsdchnData.class)) {
            UsdchnData usdchnData = new UsdchnData();
            usdchnData.setPrice(price);
            usdchnData.setChangeRate(changeRate);
            usdchnData.setTimestamp(LocalDateTime.now());
            return clazz.cast(usdchnData);
        } else if (clazz.equals(MsData.class)) {
            MsData msData = new MsData();
            msData.setPrice(price);
            msData.setChangeRate(changeRate);
            msData.setTimestamp(LocalDateTime.now());
            return clazz.cast(msData);
        }
        throw new IllegalArgumentException("Unsupported class type: " + clazz.getName());
    }
}