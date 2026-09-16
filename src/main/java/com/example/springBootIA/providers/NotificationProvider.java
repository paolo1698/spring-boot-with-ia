package com.example.springBootIA.providers;

import java.time.LocalDateTime;
import java.util.UUID;

public interface NotificationProvider {

    record Confirmation(
            UUID appointmentId,
            UUID patientId,
            LocalDateTime dateTime
    ) {}

    void notifyScheduled(Confirmation confirmation);
}