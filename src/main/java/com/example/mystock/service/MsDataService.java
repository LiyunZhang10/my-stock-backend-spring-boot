package com.example.mystock.service;

import com.example.mystock.entity.MsData;
import com.example.mystock.mapper.MsDataMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mystock.utils.SeleniumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MsDataService {
    private final MsDataMapper msDataMapper;

    @Autowired
    public MsDataService(MsDataMapper msDataMapper) {
        this.msDataMapper = msDataMapper;
    }

    public void fetchAndStoreMsData() {
        MsData msData = SeleniumUtils.getMsData();
        if (msData != null) {
            msDataMapper.insert(msData);
            System.out.println("Data saved: " + msData);
        } else {
            System.err.println("Failed to fetch data");
        }
    }

    @Transactional
    public List<MsData> getLatestMsData() {
        // 创建分页对象，1 表示第一页，300 表示每页返回 300 条数据
        Page<MsData> page = new Page<>(1, 300); // 只限制最多返回 300 条数据

        // 创建查询条件，按照 timestamp 降序排列
        QueryWrapper<MsData> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("timestamp");

        // 分页查询，返回分页结果
        IPage<MsData> msDataPage = msDataMapper.selectPage(page, queryWrapper);

        // 返回当前页的数据（最多 300 条）
        return msDataPage.getRecords();
    }
}