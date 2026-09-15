package com.example.springBootIA.controllers;
import com.example.springBootIA.requests.CreateAppointmentRequest;
import com.example.springBootIA.responses.AppointmentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    private final Map<UUID, AppointmentResponse> store =  new ConcurrentHashMap<>();
    @PostMapping
    public ResponseEntity<AppointmentResponse> schedule(
           @Valid    @RequestBody CreateAppointmentRequest request
            ){
        UUID id = UUID.randomUUID();
        AppointmentResponse appointment = new AppointmentResponse(
                id,
                request.patientId(),
                request.dateTime(),
                request.reason()
        );
        store.put(id, appointment);
        return ResponseEntity.ok(appointment);

    }
}
