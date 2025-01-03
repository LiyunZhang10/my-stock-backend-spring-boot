package com.example.mystock.repository;

import com.example.mystock.entity.MsData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface MsDataRepository extends JpaRepository<MsData, Long> {

    @Query("SELECT g FROM MsData g ORDER BY g.timestamp DESC")
    List<MsData> findLatestMsData(Pageable pageable);
}