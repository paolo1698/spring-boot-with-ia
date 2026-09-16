package com.example.springBootIA.providers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationProviderImpl implements NotificationProvider {

    private static final Logger log = LoggerFactory.getLogger(EmailNotificationProviderImpl.class);

    @Override
    public void notifyScheduled(Confirmation confirmation) {
        log.info("Enviando correo de confirmación para el paciente {} en la fecha {}",
                confirmation.patientId(),
                confirmation.dateTime()
        );
    }
}