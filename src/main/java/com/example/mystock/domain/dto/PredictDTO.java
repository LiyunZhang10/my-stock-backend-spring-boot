package com.example.mystock.domain.dto;

import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PredictDTO {
    private String status;
    private String short_term_predict;
    private String reason_short;
    private String long_term_predict;
    private String reason_long;
    private String risks_comment;

    public PredictDTO(String status) {
        this.status = status;
    }

    public PredictDTO(Predict predict){
        this.short_term_predict = predict.getShort_term_predict();
        this.reason_short = predict.getReason_short();
        this.long_term_predict = predict.getLong_term_predict();
        this.reason_long = predict.getReason_long();
        this.risks_comment = predict.getRisks_comment();
        this.status = "success";
    }
}
