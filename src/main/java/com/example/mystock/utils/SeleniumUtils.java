package com.example.mystock.utils;

import com.example.mystock.entity.GoldData;
import com.example.mystock.entity.UsdchnData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.logging.Logger;
import java.util.logging.Level;

public class SeleniumUtils {

    private static final Logger logger = Logger.getLogger(SeleniumUtils.class.getName());
    private static final String GOLD_URL = "http://quote.eastmoney.com/globalfuture/GC00Y.html";
    private static final String USDCNH_URL = "http://quote.eastmoney.com/forex/USDCNH.html";
    private static final String SELENIUM_GRID_URL = "http://localhost:4444/wd/hub";

    /**
     * 获取黄金数据
     *
     * @return GoldData 对象包含黄金价格和变动率
     */
    public static GoldData getGoldData() {
        return fetchData(GOLD_URL, "div.zxj span span", "body > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(9) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > span:nth-child(1) > span:nth-child(1) > span:nth-child(1)", GoldData.class);
    }

    /**
     * 获取美元兑人民币汇率数据
     *
     * @return UsdchnData 对象包含美元兑人民币汇率和变动率
     */
    public static UsdchnData getUsdchnData() {
        return fetchData(USDCNH_URL, "#app div[style=\"background-color:#fff\"] .container .gi_quote.self_clearfix .gi_quote_l.quote_quotenums .zxj span span", "#app div[style=\"background-color:#fff\"] .container .gi_quote.self_clearfix .gi_quote_l.quote_quotenums .zd span:nth-child(2) span", UsdchnData.class);
    }

    /**
     * 从指定的 URL 获取数据
     *
     * @param url                要获取数据的网址
     * @param priceSelector     价格元素的 CSS 选择器
     * @param changeRateSelector 变动率元素的 CSS 选择器
     * @param clazz              返回数据对象的 Class
     * @param <T>                泛型参数，表示返回的数据对象类型
     * @return 返回数据对象，如果获取数据失败则返回 null
     */
    private static <T> T fetchData(String url, String priceSelector, String changeRateSelector, Class<T> clazz) {
        WebDriver driver = null;
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            driver = new RemoteWebDriver(new URL(SELENIUM_GRID_URL), options);
            driver.get(url);

            WebElement priceElement = driver.findElement(By.cssSelector(priceSelector));
            WebElement changeRateElement = driver.findElement(By.cssSelector(changeRateSelector));

            if (priceElement != null && changeRateElement != null) {
                Double price = Double.parseDouble(priceElement.getText());
                Double changeRate = Double.parseDouble(changeRateElement.getText().replace("%", ""));

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
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching data from " + url, e);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
        return null;
    }
}