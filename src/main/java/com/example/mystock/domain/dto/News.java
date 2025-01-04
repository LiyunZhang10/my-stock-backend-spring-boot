package com.example.mystock.domain.dto;

import lombok.Data;

@Data
public class News {
    private String url;
    private String title;
    private String date;
    private String content;
    private String reason;
}
