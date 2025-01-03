package com.example.mystock.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@TableName("usdchn_data")
public class UsdchnData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;
    private Double changeRate;

    @TableField("timestamp")
    private LocalDateTime timestamp = LocalDateTime.now();
}