package com.example.mystock.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
@TableName("nvda_stock")
public class NvdaStock {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("date")
    private Date date;

    @TableField("close_price")
    private BigDecimal closePrice;

    @TableField("open_price")
    private BigDecimal openPrice;

    @TableField("high_price")
    private BigDecimal highPrice;

    @TableField("low_price")
    private BigDecimal lowPrice;
}