package com.example.mystock.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("sgdcnyc_data")
@Schema(description = "新加坡元汇率数据实体")
public class SgdcnycData {

    @Schema(description = "主键", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @Schema(description = "中间价", example = "1.0")
    @TableField("median_price")
    private String medianPrice;

    @Schema(description = "换手率", example = "0.1")
    @TableField("change_amount")
    private String changeAmount;

    @Schema(description = "变化幅度", example = "0.1%")
    @TableField("change_amplitude")
    private String changeAmplitude;

    @Schema(description = "时间戳", example = "2021-07-01T00:00:00")
    @TableField("timestamp")
    private LocalDateTime timestamp;

}