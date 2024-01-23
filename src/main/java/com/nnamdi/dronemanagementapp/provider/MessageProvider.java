package com.nnamdi.dronemanagementapp.provider;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageProvider {


    MessageSource messageSource;

    public MessageProvider(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private String getMessageByKey(String messageKey, String... params) {
        return messageSource.getMessage(messageKey, params, LocaleContextHolder.getLocale());
    }

    public String getMessage(String messageKey) {
        return getMessageByKey(messageKey);
    }

    public String getMessage(String messageKey, String... params) {
        return getMessageByKey(messageKey, params);
    }

    public String getInvalidDirection(String currentDirection, String updatedDirection) {
        return getMessageByKey("invalid.direction.identifier", currentDirection, updatedDirection);
    }

    public String getDroneAlreadyExist(String coordinateX, String coordinateY, String name) {
        return getMessageByKey("already.exist.drone", coordinateX, coordinateY, name);
    }

    public String getDroneNotFound(String id) {
        return getMessageByKey("drone.not.found", id);
    }
}
