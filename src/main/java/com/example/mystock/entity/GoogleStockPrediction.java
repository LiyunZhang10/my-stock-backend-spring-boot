package com.example.mystock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("google_stock_prediction")
@Schema(description = "谷歌股票预测实体")
public class GoogleStockPrediction {

    @Schema(description = "主键", example = "1")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "股票价格", example = "1.0")
    private Double price;

    @Schema(description = "类型", example = "past or predict")
    private PredictionType type;

    @Schema(description = "时间戳", example = "2021-07-01T00:00:00")
    private LocalDateTime timestamp;

    public enum PredictionType {
        past,
        predict
    }
}
