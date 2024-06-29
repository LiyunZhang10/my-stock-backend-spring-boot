package com.example.mystock.repository;

import com.example.mystock.entity.GoldData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface GoldDataRepository extends JpaRepository<GoldData, Long> {
    GoldData findTopByOrderByTimestampDesc();

    @Query("SELECT g FROM GoldData g ORDER BY g.timestamp DESC")
    List<GoldData> findLatestGoldData(Pageable pageable);
}