package com.nnamdi.dronemanagementapp.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = false)
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Data
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
