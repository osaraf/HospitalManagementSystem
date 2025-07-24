package com.example.hms.Service;

import com.example.hms.Model.IModelHospital;
import com.example.hms.Model.Hospital;
import com.example.hms.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class HospitalService {
    private final HospitalRepository hospitalRepository;
    private static String timestamp;

    @Autowired //das gehört zur Kommunikation!! in einer unterliegenden Schicht.
    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @Async
    public CompletableFuture<List<Hospital>> getAllHospitals() {
        // Find the Hospital instance asynchronously
        return CompletableFuture.supplyAsync(() -> {
            return hospitalRepository.findAll();
        });
    }


    @Async
    public CompletableFuture<Void> updateAllHospitals(List<Hospital> hospitals, String timeStamp) {
        return CompletableFuture.runAsync(() -> {
            timestamp = timeStamp;
            hospitalRepository.deleteAll();
            hospitals.forEach(hospital -> {
                // Füge ein neues Hospital hinzu
                hospitalRepository.save(hospital);
            });
        });
    }
    @Async
    public CompletableFuture<String> getTimestamp() {
        // Find the Hospital instance asynchronously
        return CompletableFuture.supplyAsync(() -> {
            return timestamp;
        });
    }

}
