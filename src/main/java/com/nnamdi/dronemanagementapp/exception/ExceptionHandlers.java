package com.nnamdi.dronemanagementapp.exception;

import com.nnamdi.dronemanagementapp.dto.Error;
import com.nnamdi.dronemanagementapp.dto.Response;
import com.nnamdi.dronemanagementapp.dto.ResponseCodes;
import com.nnamdi.dronemanagementapp.util.ConstantsUtil;
import com.nnamdi.dronemanagementapp.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
@Slf4j
public class ExceptionHandlers {

    private final ResponseUtil responseUtil;

    @Autowired
    public ExceptionHandlers(ResponseUtil responseUtil) {
        this.responseUtil = responseUtil;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Response> handleBadRequest(final MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<String> validationErrors = result.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        String customErrorMessage = "Validation failed. Please check your input.";
        String errorMessage = customErrorMessage + " " + String.join(", ", validationErrors);

        return ResponseEntity.badRequest().body(responseUtil.getErrorResponse(new Error(ResponseCodes.INVALID_REQUEST, ConstantsUtil.BAD_REQUEST, errorMessage)));
    }

    @ExceptionHandler(ModelAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ResponseEntity<Response> handleMethodAlreadyExist(final ModelAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseUtil.getErrorResponse(new Error(ResponseCodes.INVALID_REQUEST, ConstantsUtil.ALREADY_EXIST, ex.getMessage())));
    }
}
