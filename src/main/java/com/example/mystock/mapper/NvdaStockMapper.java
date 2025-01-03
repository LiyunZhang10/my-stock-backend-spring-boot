package com.example.mystock.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mystock.entity.NvdaStock;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface NvdaStockMapper extends BaseMapper<NvdaStock> {

    @Select("SELECT * FROM nvda_stock ORDER BY date")
    List<NvdaStock> findAll();
}