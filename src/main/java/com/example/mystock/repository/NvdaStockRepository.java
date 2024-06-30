package com.example.mystock.repository;

import com.example.mystock.entity.NvdaStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NvdaStockRepository extends JpaRepository<NvdaStock, Integer> {
}