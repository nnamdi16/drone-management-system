package com.nnamdi.dronemanagementapp.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = false)
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Data
public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}
