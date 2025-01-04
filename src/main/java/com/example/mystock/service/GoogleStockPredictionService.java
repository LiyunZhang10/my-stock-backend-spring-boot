package com.example.mystock.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mystock.mapper.GoogleStockPredictionMapper;
import com.example.mystock.entity.GoogleStockPrediction;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

@Service
public class GoogleStockPredictionService extends ServiceImpl<GoogleStockPredictionMapper, GoogleStockPrediction> {

    private GoogleStockPredictionMapper googleStockPredictionMapper;

    @Autowired
    public GoogleStockPredictionService(GoogleStockPredictionMapper googleStockPredictionMapper) {
        this.googleStockPredictionMapper = googleStockPredictionMapper;
    }

    // 获取所有预测数据
    @Transactional
    public List<GoogleStockPrediction> getLatestGoogleStockPrediction() {
        Page<GoogleStockPrediction> page = new Page<>(1, 75);

        QueryWrapper<GoogleStockPrediction> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");

        IPage<GoogleStockPrediction> googleStockPredictionPage = googleStockPredictionMapper.selectPage(page, queryWrapper);

        return googleStockPredictionPage.getRecords();
    }
}