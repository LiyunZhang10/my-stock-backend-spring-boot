package com.example.mystock.repository;

import com.example.mystock.entity.UsdchnData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface UsdchnDataRepository extends JpaRepository<UsdchnData, Long> {

    @Query("SELECT u FROM UsdchnData u ORDER BY u.timestamp DESC")
    List<UsdchnData> findLatestUsdchnData(Pageable pageable);
}