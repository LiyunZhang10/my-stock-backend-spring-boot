package com.example.mystock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mystock.entity.GoogleStockPrediction;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoogleStockPredictionMapper extends BaseMapper<GoogleStockPrediction> {
    // 这里可以添加自定义的查询方法（如果需要）
}
