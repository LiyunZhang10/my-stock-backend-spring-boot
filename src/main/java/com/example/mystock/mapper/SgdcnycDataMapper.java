package com.example.mystock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mystock.entity.SgdcnycData;
import org.apache.ibatis.annotations.Select;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

public interface SgdcnycDataMapper extends BaseMapper<SgdcnycData> {

    @Select("SELECT * FROM sgdcnyc_data ORDER BY timestamp DESC LIMIT 50")
    List<SgdcnycData> findTop50ByOrderByTimestampDesc(Page<SgdcnycData> page);
}

