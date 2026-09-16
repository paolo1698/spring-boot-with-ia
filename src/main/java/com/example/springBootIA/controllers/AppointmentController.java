package com.example.springBootIA.controllers;

import com.example.springBootIA.handlers.commands.ScheduleAppointmentHandler;
import com.example.springBootIA.handlers.queries.GetAppointmentByIdHandler;
import com.example.springBootIA.requests.CreateAppointmentRequest;
import com.example.springBootIA.responses.AppointmentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final ScheduleAppointmentHandler scheduleHandler;
    private final GetAppointmentByIdHandler getByIdHandler;

    public AppointmentController(
            ScheduleAppointmentHandler scheduleHandler,
            GetAppointmentByIdHandler getByIdHandler
    ) {
        this.scheduleHandler = scheduleHandler;
        this.getByIdHandler = getByIdHandler;
    }

    @PostMapping
    public ResponseEntity<Object> schedule(
            @Valid @RequestBody CreateAppointmentRequest request
    ) {
        var command = new ScheduleAppointmentHandler.Command(
                request.patientId(),
                request.dateTime(),
                request.reason()
        );

        var result = scheduleHandler.handle(command);

        return switch (result) {
            case ScheduleAppointmentHandler.Result.Scheduled(var id, var patientId, var dateTime, var reason) -> {
                AppointmentResponse appointment = new AppointmentResponse(id, patientId, dateTime, reason);
                yield ResponseEntity.ok(appointment);
            }
            case ScheduleAppointmentHandler.Result.SlotNotAvailable() ->
                    ResponseEntity.status(HttpStatus.CONFLICT).body("Slot not available");
        };
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        var query = new GetAppointmentByIdHandler.Query(id);
        var result = getByIdHandler.handle(query);

        return switch (result) {
            case GetAppointmentByIdHandler.Result.Found(var appointmentId, var patientId, var dateTime, var reason) -> {
                AppointmentResponse appointment = new AppointmentResponse(appointmentId, patientId, dateTime, reason);
                yield ResponseEntity.ok(appointment);
            }
            case GetAppointmentByIdHandler.Result.NotFound() ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found");
        };
    }
}