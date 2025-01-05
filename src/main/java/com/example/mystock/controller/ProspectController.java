package com.example.mystock.controller;

import com.example.mystock.domain.dto.News;
import com.example.mystock.domain.dto.NewsResult;
import com.example.mystock.service.ProspectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/prospect")
public class ProspectController {
    @Resource
    private ProspectService prospectService;

    @Operation(description = "获取利好消息")
    @GetMapping("/positive")
    public NewsResult positiveNews(
            @Parameter(description = "股票名称", required = true, example = "谷歌")
            @RequestParam String stock) throws IOException {
        NewsResult news = prospectService.positiveNews(stock);
        if(news == null || news.getNews() == null || news.getNews().isEmpty()){
            return new NewsResult("empty", null);
        }else{
            return news;
        }
    }

    @Operation(description = "获取利空消息")
    @GetMapping("/negative")
    public NewsResult negativeNews(
            @Parameter(description = "股票名称", required = true, example = "谷歌")
            @RequestParam String stock)
    {
        NewsResult news = prospectService.negativeNews(stock);
        if(news == null || news.getNews() == null || news.getNews().isEmpty()){
            return new NewsResult("empty", null);
        }else{
            return news;
        }
    }
}
