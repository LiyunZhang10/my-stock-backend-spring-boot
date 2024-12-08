package com.example.mystock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mystock.domain.dto.Predict;
import com.example.mystock.entity.NewsData;

public interface ILLMPredictService extends IService<NewsData> {
    public Predict predict(String stock);
}
