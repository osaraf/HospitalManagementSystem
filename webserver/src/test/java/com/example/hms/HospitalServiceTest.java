package com.example.hms;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.hms.Model.Hospital;
import com.example.hms.Service.HospitalService;
import com.example.hms.repository.HospitalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.concurrent.ExecutionException;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class HospitalServiceTest {

    private HospitalService hospitalService;

    @Mock
    private HospitalRepository hospitalRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        hospitalService = new HospitalService(hospitalRepository);
    }

    @Test
    public void testGetAllHospitals() throws ExecutionException, InterruptedException {
        List<Hospital> mockHospitals = new ArrayList<>();
        mockHospitals.add(new Hospital());

        when(hospitalRepository.findAll()).thenReturn(mockHospitals);

        CompletableFuture<List<Hospital>> future = hospitalService.getAllHospitals();
        List<Hospital> hospitals = future.get();

        assertNotNull(hospitals);
        assertEquals(mockHospitals, hospitals);
    }

    @Test
    public void testUpdateAllHospitals() throws ExecutionException, InterruptedException {
        List<Hospital> hospitalsToUpdate = new ArrayList<>();
        // Add some hospitals to update

        String timeStamp = "2024-01-20";

        CompletableFuture<Void> future = hospitalService.updateAllHospitals(hospitalsToUpdate, timeStamp);
        future.get();

        verify(hospitalRepository).deleteAll();
        hospitalsToUpdate.forEach(hospital ->
                verify(hospitalRepository).save(hospital)
        );

        CompletableFuture<String> timestampFuture = hospitalService.getTimestamp();
        assertEquals(timeStamp, timestampFuture.get());
    }

    @Test
    public void testGetTimestamp() throws ExecutionException, InterruptedException {
        String timeStamp = "2024-01-20";
        hospitalService.updateAllHospitals(new ArrayList<>(), timeStamp).get();

        CompletableFuture<String> future = hospitalService.getTimestamp();
        assertEquals(timeStamp, future.get());
    }
}