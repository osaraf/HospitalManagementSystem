package com.example.hms.Controller;

import com.example.hms.Model.Hospital;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

public interface IControllerHospital {
    public DeferredResult<ResponseEntity<?>> handleGetAllHospitals();
//    public DeferredResult<ResponseEntity<?>> handleGetTotalBeds();
    //public DeferredResult<ResponseEntity<?>> handleSetTotalBeds(int totalBeds);
//    public DeferredResult<ResponseEntity<?>> handleGetAvailableBeds();
    public DeferredResult<ResponseEntity<?>> handleUpdateAllHospital(List<Hospital> hospitals);
}
