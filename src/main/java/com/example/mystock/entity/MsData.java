package com.example.mystock.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("ms_data")
public class MsData {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Double price;
    private Double changeRate;

    @TableField("timestamp")
    private LocalDateTime timestamp = LocalDateTime.now();
}