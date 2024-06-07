package com.example.mystock.repository;

import com.example.mystock.entity.GoldData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoldDataRepository extends JpaRepository<GoldData, Long> {
    GoldData findTopByOrderByTimestampDesc();
}