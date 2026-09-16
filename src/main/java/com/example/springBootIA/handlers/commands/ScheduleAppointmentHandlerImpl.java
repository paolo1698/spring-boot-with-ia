package com.example.springBootIA.handlers.commands;

import com.example.springBootIA.handlers.AppointmentInMemoryStore;
import com.example.springBootIA.responses.AppointmentResponse;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class ScheduleAppointmentHandlerImpl implements ScheduleAppointmentHandler {

    private final AppointmentInMemoryStore store;

    public ScheduleAppointmentHandlerImpl(AppointmentInMemoryStore store) {
        this.store = store;
    }

    @Override
    public Result handle(Command command) {
        // 1. Validamos si el slot ya está ocupado
        boolean slotTaken = store.existsByDateTime(command.dateTime());

        if (slotTaken) {
            return new Result.SlotNotAvailable();
        }

        // 2. Si está libre, creamos el ID y la respuesta de la cita
        UUID id = UUID.randomUUID();
        AppointmentResponse appointment = new AppointmentResponse(
                id,
                command.patientId(),
                command.dateTime(),
                command.reason()
        );

        // 3. Guardamos en el store temporal
        store.save(id, appointment);

        // 4. Retornamos el resultado exitoso
        return new Result.Scheduled(
                id,
                command.patientId(),
                command.dateTime(),
                command.reason()
        );
    }
}