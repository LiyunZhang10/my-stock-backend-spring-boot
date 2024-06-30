package com.example.mystock.service;

import com.example.mystock.entity.SgdcnycData;
import com.example.mystock.repository.SgdcnycDataRepository;
import com.example.mystock.utils.SeleniumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SgdcnycDataService {

    @Autowired
    private SgdcnycDataRepository sgdcnycDataRepository;

    @Autowired
    private SeleniumUtils seleniumUtils;

    public void fetchAndSaveSgdcnycData() {
       seleniumUtils.fetchAndSaveSgdcnycData();

    }

    public List<SgdcnycData> getLatestSgdcnycData() {
        return sgdcnycDataRepository.findTop50ByOrderByTimestampDesc();
    }
}