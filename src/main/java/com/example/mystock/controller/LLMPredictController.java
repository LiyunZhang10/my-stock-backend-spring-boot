package com.example.mystock.controller;

import cn.hutool.core.bean.BeanUtil;
import com.example.mystock.domain.dto.Predict;
import com.example.mystock.domain.dto.PredictDTO;
import com.example.mystock.service.ILLMPredictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/llm")
public class LLMPredictController {
    @Resource
    private ILLMPredictService predictService;

    @Operation(description = "获取LLM预测结果")
    @GetMapping("/predict")
    public PredictDTO predict(
            @Parameter(description = "股票名称", required = true, example = "谷歌")
            @RequestParam String stock) {
        log.info("stock:{}", stock);
        Predict predict = predictService.predict(stock);
        if(predict == null){
            return new PredictDTO("error");
        }
        return new PredictDTO(predict);
    }
}
