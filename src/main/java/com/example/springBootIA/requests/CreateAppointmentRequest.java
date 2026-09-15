package com.example.springBootIA.requests;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;

//patiendID, dateTime, reason
public record CreateAppointmentRequest(
        @NotNull UUID patientId,
        @NotNull @Future LocalDateTime dateTime,
        String reason

) {
}
