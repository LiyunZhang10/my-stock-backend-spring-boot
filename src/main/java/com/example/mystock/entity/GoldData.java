package com.example.mystock.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("gold_data")
@Schema(description = "黄金数据实体")
public class GoldData {

    @Schema(description = "主键", example = "1")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "价格", example = "1.0")
    private Double price;

    @Schema(description = "变化率", example = "0.1")
    private Double changeRate;

    @Schema(description = "时间戳", example = "2021-07-01T00:00:00")
    @TableField("timestamp")
    private LocalDateTime timestamp = LocalDateTime.now();
}