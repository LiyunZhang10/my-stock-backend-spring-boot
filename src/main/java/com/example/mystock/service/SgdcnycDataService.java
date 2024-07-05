package com.example.mystock.service;

import com.example.mystock.entity.SgdcnycData;
import com.example.mystock.repository.SgdcnycDataRepository;
import com.example.mystock.utils.SeleniumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SgdcnycDataService {
    private final SgdcnycDataRepository sgdcnycDataRepository;

    private final SeleniumUtils seleniumUtils;

    @Autowired
    public SgdcnycDataService(SgdcnycDataRepository sgdcnycDataRepository, SeleniumUtils seleniumUtils) {
        this.sgdcnycDataRepository = sgdcnycDataRepository;
        this.seleniumUtils = seleniumUtils;
    }

    public void fetchAndSaveSgdcnycData() {
       seleniumUtils.fetchAndSaveSgdcnycData();

    }

    public List<SgdcnycData> getLatestSgdcnycData() {
        return sgdcnycDataRepository.findTop50ByOrderByTimestampDesc();
    }
}