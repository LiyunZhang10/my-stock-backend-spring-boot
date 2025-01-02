package com.example.mystock.domain.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class NewsResult {
    @SerializedName("reason") // 将 reason 映射为 status
    private String status;

    @SerializedName("result") // 将 result 映射为 news
    private List<News> news;

    public NewsResult(String status, List<News> news) {
        this.status = status;
        this.news = news;
    }
}
