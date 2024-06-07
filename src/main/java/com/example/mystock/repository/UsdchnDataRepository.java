package com.example.mystock.repository;


import com.example.mystock.entity.UsdchnData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsdchnDataRepository extends JpaRepository<UsdchnData, Long> {
    UsdchnData findTopByOrderByTimestampDesc();
}