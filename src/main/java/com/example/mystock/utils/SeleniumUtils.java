package com.example.mystock.utils;

import com.example.mystock.entity.GoldData;
import com.example.mystock.entity.UsdchnData;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.LocalDateTime;

public class SeleniumUtils {

    private static final String GOLD_URL = "http://quote.eastmoney.com/globalfuture/GC00Y.html";
    private static final String USDCNH_URL = "http://quote.eastmoney.com/forex/USDCNH.html";

    public static GoldData getGoldData() {
        WebDriver driver = null;
        try {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            driver = new ChromeDriver(options);
            driver.get(GOLD_URL);

            WebElement priceElement = driver.findElement(By.cssSelector("div.zxj span span"));
            WebElement changeRateElement = driver.findElement(By.cssSelector("body > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(9) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > span:nth-child(1) > span:nth-child(1) > span:nth-child(1)"));

            if (priceElement != null && changeRateElement != null) {
                Double price = Double.parseDouble(priceElement.getText());
                Double changeRate = Double.parseDouble(changeRateElement.getText().replace("%", ""));

                GoldData goldData = new GoldData();
                goldData.setPrice(price);
                goldData.setChangeRate(changeRate);
                goldData.setTimestamp(LocalDateTime.now());

                return goldData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
        return null;
    }

    public static UsdchnData getUsdchnData() {
        WebDriver driver = null;
        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            driver = new ChromeDriver(options);
            driver.get(USDCNH_URL);

            WebElement priceElement = driver.findElement(By.cssSelector("#app div[style=\"background-color:#fff\"] .container .gi_quote.self_clearfix .gi_quote_l.quote_quotenums .zxj span span"));
            WebElement changeRateElement = driver.findElement(By.cssSelector("#app div[style=\"background-color:#fff\"] .container .gi_quote.self_clearfix .gi_quote_l.quote_quotenums .zd span:nth-child(2) span"));

            if (priceElement != null && changeRateElement != null) {
                Double price = Double.parseDouble(priceElement.getText());
                Double changeRate = Double.parseDouble(changeRateElement.getText().replace("%", ""));

                UsdchnData usdchnData = new UsdchnData();
                usdchnData.setPrice(price);
                usdchnData.setChangeRate(changeRate);
                usdchnData.setTimestamp(LocalDateTime.now());

                return usdchnData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
        return null;
    }
}