package com.example.mystock.service;

import com.example.mystock.entity.NvdaStock;
import com.example.mystock.repository.NvdaStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NvdaStockService {
    @Autowired
    private NvdaStockRepository nvdaStockRepository;

    public List<NvdaStock> getAllStocks() {
        return nvdaStockRepository.findAll();
    }
}