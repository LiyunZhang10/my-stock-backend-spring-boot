package com.example.mystock.service;


import com.example.mystock.entity.MsData;
import com.example.mystock.repository.MsDataRepository;
import com.example.mystock.utils.SeleniumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MsDataService {
    private final MsDataRepository msDataRepository;

    @Autowired
    public MsDataService(MsDataRepository msDataRepository) {
        this.msDataRepository = msDataRepository;
    }

    public void fetchAndStoreMsData() {
        MsData msData = SeleniumUtils.getMsData();
        if (msData != null) {
            msDataRepository.save(msData);
            System.out.println("Data saved: " + msData);
        } else {
            System.err.println("Failed to fetch data");
        }
    }

    public List<MsData> getLatestMsData(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return msDataRepository.findLatestMsData(pageable);
    }
}