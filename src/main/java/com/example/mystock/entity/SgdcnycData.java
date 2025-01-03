package com.example.mystock.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@TableName
public class SgdcnycData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @TableField("median_price")
    private String medianPrice;

    @TableField("change_amount")
    private String changeAmount;

    @TableField("change_amplitude")
    private String changeAmplitude;

    @TableField("timestamp")
    private LocalDateTime timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedianPrice() {
        return medianPrice;
    }

    public void setMedianPrice(String medianPrice) {
        this.medianPrice = medianPrice;
    }

    public String getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(String changeAmount) {
        this.changeAmount = changeAmount;
    }

    public String getChangeAmplitude() {
        return changeAmplitude;
    }

    public void setChangeAmplitude(String changeAmplitude) {
        this.changeAmplitude = changeAmplitude;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}