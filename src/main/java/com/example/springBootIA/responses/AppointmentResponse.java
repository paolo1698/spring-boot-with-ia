package com.example.springBootIA.responses;

import java.time.LocalDateTime;
import java.util.UUID;

//id, patientId, dateTime, reason
public record AppointmentResponse(
        UUID id,
        UUID patientId,
        LocalDateTime dateTime,
        String reason
) {
}
