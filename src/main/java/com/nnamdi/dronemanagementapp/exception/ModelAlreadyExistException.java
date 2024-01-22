package com.nnamdi.dronemanagementapp.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@EqualsAndHashCode(callSuper = false)
@ResponseStatus(value = HttpStatus.CONFLICT)
public class ModelAlreadyExistException extends RuntimeException {
    public ModelAlreadyExistException(String message){
        super(message);
    }

}
