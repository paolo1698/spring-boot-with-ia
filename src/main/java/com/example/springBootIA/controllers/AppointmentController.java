package com.example.springBootIA.controllers;

import com.example.springBootIA.handlers.commands.ScheduleAppointmentHandler;
import com.example.springBootIA.requests.CreateAppointmentRequest;
import com.example.springBootIA.responses.AppointmentResponse;
import org.springframework.http.HttpStatus;
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

    // Almacén temporal en memoria visto en este capítulo del video
    private final Map<UUID, AppointmentResponse> store = new ConcurrentHashMap<>();

    @PostMapping
    public ResponseEntity<Object> schedule(
            @Valid @RequestBody CreateAppointmentRequest request
    ) {
        // 1. Mapeamos el Request HTTP hacia el Command del CQRS
        var command = new ScheduleAppointmentHandler.Command(
                request.patientId(),
                request.dateTime(),
                request.reason()
        );

        // 2. Resolvemos la lógica llamando al método privado
        var result = resolve(command);

        // 3. Evaluamos el resultado usando Switch Expression y Pattern Matching
        return switch (result) {
            case ScheduleAppointmentHandler.Result.Scheduled(var id, var patientId, var dateTime, var reason) -> {
                AppointmentResponse appointment = new AppointmentResponse(id, patientId, dateTime, reason);
                yield ResponseEntity.ok(appointment);
            }
            case ScheduleAppointmentHandler.Result.SlotNotAvailable() ->
                    ResponseEntity.status(HttpStatus.CONFLICT).body("Slot not available");
        };
    }

    private ScheduleAppointmentHandler.Result resolve(ScheduleAppointmentHandler.Command command) {
        // Validamos si ya existe una cita en el mismo horario
        boolean slotTaken = store.values().stream()
                .anyMatch(existing -> existing.dateTime().equals(command.dateTime()));

        if (slotTaken) {
            return new ScheduleAppointmentHandler.Result.SlotNotAvailable();
        }

        // Si el slot está libre, creamos y guardamos la cita
        UUID id = UUID.randomUUID();
        AppointmentResponse appointment = new AppointmentResponse(
                id,
                command.patientId(),
                command.dateTime(),
                command.reason()
        );

        store.put(id, appointment);

        return new ScheduleAppointmentHandler.Result.Scheduled(
                id,
                command.patientId(),
                command.dateTime(),
                command.reason()
        );
    }
}