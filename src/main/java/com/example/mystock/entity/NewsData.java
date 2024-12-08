package com.example.mystock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("news_data")
public class NewsData implements Serializable {
    @TableId(value = "news_id", type = IdType.AUTO)
    private Long newsId;

    @TableField("stock")
    private String stock;

    @TableField("title")
    private String title;

    @TableField("newstime")
    private LocalDateTime newstime;
}
