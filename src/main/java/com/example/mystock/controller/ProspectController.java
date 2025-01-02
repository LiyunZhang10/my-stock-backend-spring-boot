package com.example.mystock.controller;

import com.example.mystock.domain.dto.News;
import com.example.mystock.domain.dto.NewsResult;
import com.example.mystock.service.ProspectService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/prospect")
public class ProspectController {
    @Resource
    private ProspectService prospectService;

    @RequestMapping("/positive")
    public NewsResult positiveNews(@RequestParam String stock) throws IOException {
        NewsResult news = prospectService.positiveNews(stock);
        if(news.getNews() == null || news.getNews().isEmpty()){
            return new NewsResult("empty", null);
        }else{
            return news;
        }
    }

    @RequestMapping("/negative")
    public NewsResult negativeNews(@RequestParam String stock)
    {
        NewsResult news = prospectService.negativeNews(stock);
        if(news.getNews() == null || news.getNews().isEmpty()){
            return new NewsResult("empty", null);
        }else{
            return news;
        }
    }
}
