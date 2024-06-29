package com.example.mystock.service;

import com.example.mystock.entity.GoldData;
import com.example.mystock.repository.GoldDataRepository;
import com.example.mystock.utils.SeleniumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GoldDataService {

    @Autowired
    private GoldDataRepository goldDataRepository;

    public void fetchAndStoreGoldData() {
        GoldData goldData = SeleniumUtils.getGoldData();
        if (goldData != null) {
            goldDataRepository.save(goldData);
            System.out.println("Data saved: " + goldData);
        } else {
            System.err.println("Failed to fetch data");
        }
    }

    public GoldData getLatestGoldData() {
        return goldDataRepository.findTopByOrderByTimestampDesc();
    }

    public List<GoldData> getLatestGoldData(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return goldDataRepository.findLatestGoldData(pageable);
    }
}