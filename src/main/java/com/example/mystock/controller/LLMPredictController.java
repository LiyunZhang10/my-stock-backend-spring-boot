package com.example.mystock.controller;

import cn.hutool.core.bean.BeanUtil;
import com.example.mystock.domain.dto.Predict;
import com.example.mystock.domain.dto.PredictDTO;
import com.example.mystock.domain.dto.StockDTO;
import com.example.mystock.service.ILLMPredictService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/llm")
public class LLMPredictController {
    @Resource
    private ILLMPredictService predictService;

    @PostMapping("/predict")
    public PredictDTO predict(@RequestBody StockDTO stock) {
        log.info("stock:{}", stock.getStock());
        Predict predict = predictService.predict(stock.getStock());
        if(predict == null){
            return new PredictDTO("error");
        }
        return new PredictDTO(predict);
    }
}
