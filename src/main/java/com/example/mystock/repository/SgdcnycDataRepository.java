package com.example.mystock.repository;

import com.example.mystock.entity.SgdcnycData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SgdcnycDataRepository extends JpaRepository<SgdcnycData, Integer> {
    List<SgdcnycData> findTop50ByOrderByTimestampDesc();
}