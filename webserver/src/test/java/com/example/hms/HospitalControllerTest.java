package com.example.hms;

import com.example.hms.Controller.ControllerHospital;
import com.example.hms.Model.Hospital;
import com.example.hms.Service.HospitalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
public class HospitalControllerTest {

    private MockMvc mockMvc;

    @Mock
    private HospitalService hospitalService;

    @InjectMocks
    private ControllerHospital controllerHospital;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controllerHospital).build();
    }

    @Test
    public void testGetAllHospitals() throws Exception {
        List<Hospital> hospitals = new ArrayList<>();
        hospitals.add(new Hospital());
        when(hospitalService.getAllHospitals()).thenReturn(CompletableFuture.completedFuture(hospitals));

        mockMvc.perform(get("/getAllHospitals"))
                .andExpect(status().isOk());

        verify(hospitalService, times(1)).getAllHospitals();
    }

    @Test
    public void testHandleUpdateAllHospital() throws Exception {
        List<Hospital> hospitalsToUpdate = new ArrayList<>(); // Initialize with test data

        when(hospitalService.updateAllHospitals(anyList(), anyString())).thenReturn(CompletableFuture.completedFuture(null));

        ObjectMapper objectMapper = new ObjectMapper();
        String hospitalsJson = objectMapper.writeValueAsString(hospitalsToUpdate);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/updateAllHospitals")
                        .content(hospitalsJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Verfügbare Betten erfolgreich aktualisiert"));

        verify(hospitalService, times(1)).updateAllHospitals(anyList(), anyString());
    }

    @Test
    public void testGetTimestamp() throws Exception {
        String expectedTimestamp = "20.01.24 12:45:13";
        when(hospitalService.getTimestamp()).thenReturn(CompletableFuture.completedFuture(expectedTimestamp));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/getTimestamp"))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedTimestamp));

        verify(hospitalService, times(1)).getTimestamp();
    }

}