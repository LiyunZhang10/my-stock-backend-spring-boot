package com.example.mystock.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.logging.Logger;
import java.util.logging.Level;

public class CssSelectorTester {

    private static final Logger logger = Logger.getLogger(CssSelectorTester.class.getName());
    private static final String SELENIUM_GRID_URL = "http://localhost:4444/wd/hub";

    public static void main(String[] args) {
        testCssSelector("http://quote.eastmoney.com/globalfuture/GC00Y.html", "div[class=\"zxj\"] span span");
        testCssSelector("http://quote.eastmoney.com/globalfuture/GC00Y.html", "body > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(9) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > span:nth-child(1) > span:nth-child(1) > span:nth-child(1)");
        testCssSelector("http://quote.eastmoney.com/forex/USDCNH.html", "#app div[style=\"background-color:#fff\"] .container .gi_quote.self_clearfix .gi_quote_l.quote_quotenums .zxj span span");
        testCssSelector("http://quote.eastmoney.com/forex/USDCNH.html", "#app div[style=\"background-color:#fff\"] .container .gi_quote.self_clearfix .gi_quote_l.quote_quotenums .zd span:nth-child(2) span");
    }

    private static void testCssSelector(String url, String cssSelector) {
        WebDriver driver = null;
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            driver = new RemoteWebDriver(new URL(SELENIUM_GRID_URL), options);
            driver.get(url);

            WebElement element = driver.findElement(By.cssSelector(cssSelector));
            if (element != null) {
                logger.info("Successfully found element with CSS selector: " + cssSelector + " on URL: " + url);
            } else {
                logger.warning("Could not find element with CSS selector: " + cssSelector + " on URL: " + url);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error testing CSS selector: " + cssSelector + " on URL: " + url, e);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}