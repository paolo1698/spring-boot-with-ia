package com.example.springBootIA.handlers.queries;

import com.example.springBootIA.handlers.AppointmentInMemoryStore;
import org.springframework.stereotype.Component;

@Component
public class GetAppointmentByIdHandlerImpl implements GetAppointmentByIdHandler {

    private final AppointmentInMemoryStore store;

    public GetAppointmentByIdHandlerImpl(AppointmentInMemoryStore store) {
        this.store = store;
    }

    @Override
    public Result handle(Query query) {
        return store.findById(query.id())
                .map(appointment -> (Result) new Result.Found(
                        appointment.id(),
                        appointment.patientId(),
                        appointment.dateTime(),
                        appointment.reason()
                ))
                .orElseGet(Result.NotFound::new);
    }
}