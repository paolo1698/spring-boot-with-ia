package com.example.springBootIA.handlers.queries;

import com.example.springBootIA.persistence.repositories.AppointmentRepository;
import org.springframework.stereotype.Component;

@Component
public class GetAppointmentByIdHandlerImpl implements GetAppointmentByIdHandler {

    private final AppointmentRepository repository;

    public GetAppointmentByIdHandlerImpl(AppointmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result handle(Query query) {
        return repository.findById(query.id())
                .map(entity -> (Result) new Result.Found(
                        entity.getId(),
                        entity.getPatientId(),
                        entity.getDateTime(),
                        entity.getReason()
                ))
                .orElseGet(Result.NotFound::new);
    }
}