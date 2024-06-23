package com.example.mystock.controller;

import com.example.mystock.entity.GoldData;
import com.example.mystock.entity.UsdchnData;
import com.example.mystock.service.GoldDataService;
import com.example.mystock.service.UsdchnDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DataController {

    @Autowired
    private GoldDataService goldDataService;

    @Autowired
    private UsdchnDataService usdchnDataService;

    @Scheduled(fixedRate = 15000)
    public void fetchData() {
        try {
            System.out.println("Fetching gold data...");
            goldDataService.fetchAndStoreGoldData();
            System.out.println("Fetching USD/CNH data...");
            usdchnDataService.fetchAndStoreUsdchnData();
        } catch (Exception e) {
            System.err.println("Error fetching data: " + e.getMessage());
        }
    }

    @GetMapping("/latest-gold-data")
    public GoldData getLatestGoldData() {
        return goldDataService.getLatestGoldData();
    }

    @GetMapping("/latest-usdchn-data")
    public UsdchnData getLatestUsdchnData() {
        return usdchnDataService.getLatestUsdchnData();
    }
}