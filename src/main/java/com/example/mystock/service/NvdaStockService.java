package com.example.mystock.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mystock.entity.NvdaStock;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mystock.mapper.NvdaStockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class NvdaStockService {

    private final NvdaStockMapper nvdaStockMapper;

    @Autowired
    public NvdaStockService(NvdaStockMapper nvdaStockMapper) {
        this.nvdaStockMapper = nvdaStockMapper;
    }

    @Transactional
    public List<NvdaStock> getNvdaStock() {

        Page<NvdaStock> page = new Page<>(1, 50);

        QueryWrapper<NvdaStock> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("date");

        IPage<NvdaStock> nvdaStockPage = nvdaStockMapper.selectPage(page, queryWrapper);

        return nvdaStockPage.getRecords();
    }
}