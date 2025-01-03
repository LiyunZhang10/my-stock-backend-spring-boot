package com.example.mystock.service;

import com.example.mystock.entity.SgdcnycData;
import com.example.mystock.mapper.SgdcnycDataMapper;
import com.example.mystock.utils.SeleniumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

@Service
public class SgdcnycDataService {

    private final SgdcnycDataMapper sgdcnycDataMapper;
    private final SeleniumUtils seleniumUtils;

    @Autowired
    public SgdcnycDataService(SgdcnycDataMapper sgdcnycDataMapper, SeleniumUtils seleniumUtils) {
        this.sgdcnycDataMapper = sgdcnycDataMapper;
        this.seleniumUtils = seleniumUtils;
    }

    // 使用 Selenium 获取并保存数据
    public void fetchAndSaveSgdcnycData() {
        seleniumUtils.fetchAndSaveSgdcnycData();
    }

    public List<SgdcnycData> getLatestSgdcnycData() {
        // 创建一个分页对象，页码从 1 开始，每页 50 条数据
        Page<SgdcnycData> page = new Page<>(1, 50);  // 第一个参数为页码，第二个参数为每页条数

        // 使用 selectPage 方法进行分页查询
        Page<SgdcnycData> result = sgdcnycDataMapper.selectPage(page, null);

        // 返回查询结果
        return result.getRecords();  // getRecords() 方法返回当前页的数据列表
    }
}
