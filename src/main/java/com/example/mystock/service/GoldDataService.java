package com.example.mystock.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mystock.entity.GoldData;
import com.example.mystock.mapper.GoldDataMapper;
import com.example.mystock.utils.SeleniumUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class GoldDataService {
    private final GoldDataMapper goldDataMapper;

    @Autowired
    public GoldDataService(GoldDataMapper goldDataMapper) {
        this.goldDataMapper = goldDataMapper;
    }

    public void fetchAndStoreGoldData() {
        GoldData goldData = SeleniumUtils.getGoldData();
        if (goldData != null) {
            goldDataMapper.insert(goldData);
            log.info("Data saved: " + goldData);
        } else {
            log.error("Failed to fetch data");
        }
    }

    @Transactional
    public List<GoldData> getLatestGoldData() {
        // 创建分页对象，1 表示第一页，300 表示每页返回 300 条数据
        Page<GoldData> page = new Page<>(1, 300); // 只限制最多返回 300 条数据

        // 创建查询条件，按照 timestamp 降序排列
        QueryWrapper<GoldData> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("timestamp");

        // 分页查询，返回分页结果
        IPage<GoldData> goldDataPage = goldDataMapper.selectPage(page, queryWrapper);

        // 返回当前页的数据（最多 300 条）
        return goldDataPage.getRecords();
    }
}