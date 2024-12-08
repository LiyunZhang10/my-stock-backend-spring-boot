package com.example.mystock.domain.dto;

import lombok.Data;

@Data
public class Predict {
    private String short_term_predict;
    private String reason_short;
    private String long_term_predict;
    private String reason_long;
    private String risks_comment;
}
