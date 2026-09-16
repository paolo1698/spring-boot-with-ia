package com.example.springBootIA.handlers.commands;

import com.example.springBootIA.handlers.AppointmentInMemoryStore;
import com.example.springBootIA.providers.NotificationProvider;
import com.example.springBootIA.responses.AppointmentResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ScheduleAppointmentHandlerImpl implements ScheduleAppointmentHandler {

    private final AppointmentInMemoryStore store;
    private final NotificationProvider notificationProvider; // <--- Nueva dependencia inyectada

    public ScheduleAppointmentHandlerImpl(
            AppointmentInMemoryStore store,
            NotificationProvider notificationProvider
    ) {
        this.store = store;
        this.notificationProvider = notificationProvider;
    }

    @Override
    public Result handle(Command command) {
        boolean slotTaken = store.existsByDateTime(command.dateTime());

        if (slotTaken) {
            return new Result.SlotNotAvailable();
        }

        UUID id = UUID.randomUUID();
        AppointmentResponse appointment = new AppointmentResponse(
                id,
                command.patientId(),
                command.dateTime(),
                command.reason()
        );

        store.save(id, appointment);

        // <--- Disparamos la notificación usando el provider desacoplado
        notificationProvider.notifyScheduled(
                new NotificationProvider.Confirmation(
                        id,
                        command.patientId(),
                        command.dateTime()
                )
        );

        return new Result.Scheduled(
                id,
                command.patientId(),
                command.dateTime(),
                command.reason()
        );
    }
}