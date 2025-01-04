package com.example.mystock.controller;

import com.example.mystock.entity.GoldData;
import com.example.mystock.entity.MsData;
import com.example.mystock.entity.UsdchnData;
import com.example.mystock.entity.NvdaStock;
import com.example.mystock.entity.SgdcnycData;
import com.example.mystock.entity.GoogleStockPrediction;
import com.example.mystock.service.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DataController {

    private final GoldDataService goldDataService;
    private final MsDataService msDataService;
    private final UsdchnDataService usdchnDataService;
    private final NvdaStockService nvdaStockService;
    private final SgdcnycDataService sgdcnycDataService;
    private final GoogleStockPredictionService googleStockPredictionService;

    public DataController(GoldDataService goldDataService,
                          MsDataService msDataService,
                          UsdchnDataService usdchnDataService,
                          NvdaStockService nvdaStockService,
                          SgdcnycDataService sgdcnycDataService,
                          GoogleStockPredictionService googleStockPredictionService) {
        this.goldDataService = goldDataService;
        this.msDataService = msDataService;
        this.usdchnDataService = usdchnDataService;
        this.nvdaStockService = nvdaStockService;
        this.sgdcnycDataService = sgdcnycDataService;
        this.googleStockPredictionService = googleStockPredictionService;
    }

    @Scheduled(fixedRate = 60000)
    public void fetchData() {
        try {
            System.out.println("Fetching gold data...");
            goldDataService.fetchAndStoreGoldData();
            System.out.println("Fetching MS stocks...");
            msDataService.fetchAndStoreMsData();
            System.out.println("Fetching USD/CNH data...");
            usdchnDataService.fetchAndStoreUsdchnData();
            System.out.println("Fetching SGD/CNYC data...");
            sgdcnycDataService.fetchAndSaveSgdcnycData();
        } catch (Exception e) {
            System.err.println("Error fetching data: " + e.getMessage());
        }
    }

    @GetMapping("/latest-gold-data")
    public List<GoldData> getLatestGoldData() {
        System.out.println("Accessing latest gold data");
        return goldDataService.getLatestGoldData();
    }

    @GetMapping("/latest-ms-data")
    public List<MsData> getLatestMsData() {
        System.out.println("Accessing latest MS data");
        return msDataService.getLatestMsData();
    }

    @GetMapping("/latest-usdchn-data")
    public List<UsdchnData> getLatestUsdchnData() {
        System.out.println("Accessing latest usdchn data");
        return usdchnDataService.getLatestUsdchnData();
    }

    @GetMapping("/nvda-stocks")
    public List<NvdaStock> getAllStocks() {
        System.out.println("Accessing all NVDA stocks");
        return nvdaStockService.getAllStocks();
    }

    @GetMapping("/latest-sgdcnyc-data")
    public List<SgdcnycData> getLatestSgdcnycData() {
        System.out.println("Accessing latest SGD/CNYC data");
        return sgdcnycDataService.getLatestSgdcnycData();
    }

    @GetMapping("/google-stock-prediction")
    public List<GoogleStockPrediction> getGoogleStockPredictions() {
        System.out.println("Accessing Google stock predictions");
        return googleStockPredictionService.getLatestGoogleStockPrediction();
    }
}