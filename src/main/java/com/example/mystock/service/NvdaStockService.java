package com.example.mystock.service;

import com.example.mystock.entity.NvdaStock;
import com.example.mystock.mapper.NvdaStockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NvdaStockService {
    private final NvdaStockMapper nvdaStockMapper;

    @Autowired
    public NvdaStockService(NvdaStockMapper nvdaStockMapper) {
        this.nvdaStockMapper = nvdaStockMapper;
    }

    public List<NvdaStock> getAllStocks() {
        return nvdaStockMapper.findAll();
    }
}