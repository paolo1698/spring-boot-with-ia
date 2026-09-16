package com.example.springBootIA.handlers.commands;

import com.example.springBootIA.persistence.model.AppointmentEntity;
import com.example.springBootIA.persistence.repositories.AppointmentRepository;
import com.example.springBootIA.providers.NotificationProvider;
import com.example.springBootIA.responses.AppointmentResponse;
import org.springframework.stereotype.Component;

@Component
public class ScheduleAppointmentHandlerImpl implements ScheduleAppointmentHandler {

    private final AppointmentRepository repository;
    private final NotificationProvider notificationProvider;

    public ScheduleAppointmentHandlerImpl(
            AppointmentRepository repository,
            NotificationProvider notificationProvider
    ) {
        this.repository = repository;
        this.notificationProvider = notificationProvider;
    }

    @Override
    public Result handle(Command command) {
        boolean slotTaken = repository.existsByDateTime(command.dateTime());

        if (slotTaken) {
            return new Result.SlotNotAvailable();
        }

        // Creamos la entidad JPA (el ID se autogenera en la base de datos)
        AppointmentEntity entity = new AppointmentEntity(
                command.patientId(),
                command.dateTime(),
                command.reason()
        );

        AppointmentEntity savedEntity = repository.save(entity);

        // Notificamos usando los datos guardados
        notificationProvider.notifyScheduled(
                new NotificationProvider.Confirmation(
                        savedEntity.getId(),
                        savedEntity.getPatientId(),
                        savedEntity.getDateTime()
                )
        );

        return new Result.Scheduled(
                savedEntity.getId(),
                savedEntity.getPatientId(),
                savedEntity.getDateTime(),
                savedEntity.getReason()
        );
    }
}