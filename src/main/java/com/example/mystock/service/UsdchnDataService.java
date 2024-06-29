package com.example.mystock.service;

import com.example.mystock.entity.UsdchnData;
import com.example.mystock.repository.UsdchnDataRepository;
import com.example.mystock.utils.SeleniumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsdchnDataService {

    @Autowired
    private UsdchnDataRepository usdchnDataRepository;

    public void fetchAndStoreUsdchnData() {
        UsdchnData usdchnData = SeleniumUtils.getUsdchnData();
        if (usdchnData != null) {
            usdchnDataRepository.save(usdchnData);
            System.out.println("Data saved: " + usdchnData);
        } else {
            System.err.println("Failed to fetch data");
        }
    }

    public UsdchnData getLatestUsdchnData() {
        return usdchnDataRepository.findTopByOrderByTimestampDesc();
    }

    public List<UsdchnData> getLatestUsdchnData(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return usdchnDataRepository.findLatestUsdchnData(pageable);
    }
}