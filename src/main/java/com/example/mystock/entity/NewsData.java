package com.example.mystock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("news_data")
@Schema(description = "新闻数据实体")
public class NewsData implements Serializable {

    @Schema(description = "主键", example = "1")
    @TableId(value = "news_id", type = IdType.AUTO)
    private Long newsId;

    @TableField("stock")
    private String stock;

    @TableField("title")
    private String title;

    @TableField("newstime")
    private LocalDateTime newstime;
}
