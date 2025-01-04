package com.example.mystock.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("nvda_stock")
@Schema(description = "Nvda股票数据实体")
public class NvdaStock {

    @Schema(description = "主键", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @Schema(description = "时间戳", example = "2021-07-01T00:00:00")
    @TableField("date")
    private Date date;

    @Schema(description = "收盘价", example = "1.0")
    @TableField("close_price")
    private BigDecimal closePrice;

    @Schema(description = "开盘价", example = "1.0")
    @TableField("open_price")
    private BigDecimal openPrice;

    @Schema(description = "最高价", example = "1.0")
    @TableField("high_price")
    private BigDecimal highPrice;

    @Schema(description = "最低价", example = "1.0")
    @TableField("low_price")
    private BigDecimal lowPrice;
}