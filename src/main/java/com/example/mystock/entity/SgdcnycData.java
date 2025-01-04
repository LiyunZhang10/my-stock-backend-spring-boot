package com.example.mystock.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName
public class SgdcnycData {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("median_price")
    private String medianPrice;

    @TableField("change_amount")
    private String changeAmount;

    @TableField("change_amplitude")
    private String changeAmplitude;

    @TableField("timestamp")
    private LocalDateTime timestamp;

}