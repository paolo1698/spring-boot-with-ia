package com.example.springBootIA.handlers;

import com.example.springBootIA.responses.AppointmentResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AppointmentInMemoryStore {

    private final Map<UUID, AppointmentResponse> store = new ConcurrentHashMap<>();

    public void save(UUID id, AppointmentResponse appointment) {
        store.put(id, appointment);
    }

    public Optional<AppointmentResponse> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public boolean existsByDateTime(LocalDateTime dateTime) {
        return store.values().stream()
                .anyMatch(existing -> existing.dateTime().equals(dateTime));
    }
}