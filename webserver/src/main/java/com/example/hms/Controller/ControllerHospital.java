package com.example.hms.Controller;

import com.example.hms.Model.Hospital;
import com.example.hms.Service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@CrossOrigin
@RestController
public class ControllerHospital implements IControllerHospital {

    @Autowired
    private HospitalService hospitalService;

//    @GetMapping("/availableBeds")
//    @Override
//    public DeferredResult<ResponseEntity<?>> handleGetAvailableBeds() {
//        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();
//
//        // Asynchronen Service aufrufen
//        CompletableFuture<List<Hospital>> availableBedsFuture = krankenhausService.getAvailableBeds();
//
//
//
//        // Setze das Ergebnis des DeferredResult, sobald es verfügbar ist
//        availableBedsFuture.thenAccept(availableBeds ->
//                deferredResult.setResult(ResponseEntity.ok(availableBeds))
//        ).exceptionally(ex -> {
//            // Fehlerbehandlung
//            deferredResult.setErrorResult(
//                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                            .body("Fehler bei der Abfrage der verfügbaren Betten")
//            );
//            return null;
//        });
//
//        return deferredResult;
//    }


    @GetMapping("/getAllHospitals")
    @Override
    public DeferredResult<ResponseEntity<?>> handleGetAllHospitals() {
        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();

        // Asynchronen Service aufrufen
        CompletableFuture<List<Hospital>> availableBedsFuture = hospitalService.getAllHospitals();


        // Setze das Ergebnis des DeferredResult, sobald es verfügbar ist
        availableBedsFuture.thenAccept(hospitals ->
                deferredResult.setResult(ResponseEntity.ok(hospitals))
        ).exceptionally(ex -> {
            // Fehlerbehandlung
            deferredResult.setErrorResult(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Fehler bei der Abfrage der verfügbaren Betten")
            );
            return null;
        });

        return deferredResult;
    }

    @GetMapping("/getTimestamp")
    public DeferredResult<ResponseEntity<?>> getTimestamp() {
        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();

        // Asynchronen Service aufrufen
        CompletableFuture<String> timestampFuture = hospitalService.getTimestamp();


        // Setze das Ergebnis des DeferredResult, sobald es verfügbar ist
        timestampFuture.thenAccept(timestamp ->
                deferredResult.setResult(ResponseEntity.ok(timestamp))
        ).exceptionally(ex -> {
            // Fehlerbehandlung
            deferredResult.setErrorResult(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Fehler bei der Abfrage der verfügbaren Betten")
            );
            return null;
        });

        return deferredResult;
    }



    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/updateAllHospitals")
    public DeferredResult<ResponseEntity<?>> handleUpdateAllHospital(@RequestBody List<Hospital> hospitals) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yy HH:mm:ss"));
        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();

        // Asynchronen Service aufrufen
        CompletableFuture<Void> updateAllHospitalsFuture = hospitalService.updateAllHospitals(hospitals,timestamp);
        updateAllHospitalsFuture.thenRun(() ->
                deferredResult.setResult(ResponseEntity.status(HttpStatus.ACCEPTED).body("Verfügbare Betten erfolgreich aktualisiert"))
        ).exceptionally(ex -> {
            // Fehlerbehandlung
            deferredResult.setErrorResult(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Fehler bei der Aktualisierung der verfügbaren Betten")
            );
            return null;
        });

        return deferredResult;
    }

//    @GetMapping("/totalBeds")
//    @Override
//    public DeferredResult<ResponseEntity<?>> handleGetTotalBeds() {
//        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();
//
//        // Asynchronen Service aufrufen
//        CompletableFuture<Integer> totalBedsFuture = hospitalService.getTotalBeds();
//
//        // Setze das Ergebnis des DeferredResult, sobald es verfügbar ist
//        totalBedsFuture.thenAccept(totalBeds ->
//                deferredResult.setResult(ResponseEntity.ok(totalBeds))
//        ).exceptionally(ex -> {
//            // Fehlerbehandlung
//            deferredResult.setErrorResult(
//                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                            .body("Fehler bei der Abfrage der total Betten")
//            );
//            return null;
//        });
//
//        return deferredResult;
//    }
//
//
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    @PostMapping("/setTotalBeds")
//    @Override
//    public DeferredResult<ResponseEntity<?>> handleSetTotalBeds(@RequestParam int totalBeds) {
//        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();
//
//        // Asynchronen Service aufrufen
//        CompletableFuture<Void> setTotalBedsFuture = hospitalService.setTotalBeds(totalBeds);
//
//        // Setze das Ergebnis des DeferredResult, sobald es verfügbar ist
//        setTotalBedsFuture.thenRun(() ->
//                deferredResult.setResult(ResponseEntity.ok("Totale Betten erfolgreich aktualisiert"))
//        ).exceptionally(ex -> {
//            // Fehlerbehandlung
//            deferredResult.setErrorResult(
//                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                            .body("Fehler bei der Aktualisierung der totalen Betten")
//            );
//            return null;
//        });
//
//        return deferredResult;
//    }


}
