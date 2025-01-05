package com.example.mystock.controller;

import com.example.mystock.entity.*;
import com.example.mystock.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Slf4j
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
            log.info("Fetching gold data...");
            goldDataService.fetchAndStoreGoldData();
            log.info("Fetching MS stocks...");
            msDataService.fetchAndStoreMsData();
            log.info("Fetching USD/CNH data...");
            usdchnDataService.fetchAndStoreUsdchnData();
            log.info("Fetching SGD/CNYC data...");
            sgdcnycDataService.fetchAndSaveSgdcnycData();
        } catch (Exception e) {
            log.error("Error fetching data: {}", e.getMessage());
        }
    }

    @GetMapping("/latest-gold-data")
    public List<GoldData> getLatestGoldData() {
        log.info("Accessing latest gold data");
        return goldDataService.getLatestGoldData();
    }

    @GetMapping("/latest-ms-data")
    public List<MsData> getLatestMsData() {
        log.info("Accessing latest MS data");
        return msDataService.getLatestMsData();
    }

    @GetMapping("/latest-usdchn-data")
    public List<UsdchnData> getLatestUsdchnData() {
        log.info("Accessing latest usdchn data");
        return usdchnDataService.getLatestUsdchnData();
    }

    @GetMapping("/nvda-stocks")
    public List<NvdaStock> getAllStocks() {
        log.info("Accessing all NVDA stocks");
        return nvdaStockService.getNvdaStock();
    }

    @GetMapping("/latest-sgdcnyc-data")
    public List<SgdcnycData> getLatestSgdcnycData() {
        log.info("Accessing latest SGD/CNYC data");
        return sgdcnycDataService.getLatestSgdcnycData();
    }

    @GetMapping("/google-stock-prediction")
    public List<GoogleStockPrediction> getGoogleStockPredictions() {
        log.info("Accessing Google stock predictions");
        return googleStockPredictionService.getLatestGoogleStockPrediction();
    }
}