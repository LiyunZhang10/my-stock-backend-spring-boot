package com.example.mystock.controller;

import com.example.mystock.entity.GoldData;
import com.example.mystock.entity.UsdchnData;
import com.example.mystock.entity.NvdaStock;
import com.example.mystock.service.NvdaStockService;
import com.example.mystock.service.GoldDataService;
import com.example.mystock.service.UsdchnDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DataController {

    @Autowired
    private GoldDataService goldDataService;

    @Autowired
    private UsdchnDataService usdchnDataService;

    @Autowired
    private NvdaStockService nvdaStockService;

//    @Scheduled(fixedRate = 30000)
//    public void fetchData() {
//        try {
//            System.out.println("Fetching gold data...");
//            goldDataService.fetchAndStoreGoldData();
//            System.out.println("Fetching USD/CNH data...");
//            usdchnDataService.fetchAndStoreUsdchnData();
//        } catch (Exception e) {
//            System.err.println("Error fetching data: " + e.getMessage());
//        }
//    }

    @GetMapping("/latest-gold-data")
    public List<GoldData> getLatestGoldData() {
        System.out.println("Accessing latest gold data");
        return goldDataService.getLatestGoldData(300);
    }

    @GetMapping("/latest-usdchn-data")
    public List<UsdchnData> getLatestUsdchnData() {
        System.out.println("Accessing latest usdchn data");
        return usdchnDataService.getLatestUsdchnData(300);
    }

    @GetMapping("/nvda-stocks")
    public List<NvdaStock> getAllStocks() {
        return nvdaStockService.getAllStocks();
    }
}