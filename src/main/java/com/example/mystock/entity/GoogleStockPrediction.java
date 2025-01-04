package com.example.mystock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("google_stock_prediction")  // 映射数据库表
public class GoogleStockPrediction {

    @TableId(type = IdType.AUTO)  // 设置主键自增
    private Long id;  // 自动生成的主键

    private Double price;  // 股票价格

    private PredictionType type;  // 类型 (past 或 predict)

    private LocalDateTime timestamp;  // 时间戳

    // 枚举类型，表示数据类型
    public enum PredictionType {
        past,
        predict
    }
}
