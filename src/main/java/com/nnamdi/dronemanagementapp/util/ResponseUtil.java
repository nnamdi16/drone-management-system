package com.nnamdi.dronemanagementapp.util;

import com.nnamdi.dronemanagementapp.dto.Error;
import com.nnamdi.dronemanagementapp.dto.Response;
import com.nnamdi.dronemanagementapp.dto.ResponseCodes;
import org.springframework.stereotype.Component;

@Component
public class ResponseUtil {
    public <T> Response<T> getSuccessResponse(T data) {
        return new Response<>(ResponseCodes.SUCCESS.code(), ConstantsUtil.SUCCESSFUL, data, null);
    }

    public <T> Response<T> getErrorResponse(Error err) {
        return new Response<>(err.getCode(), err.getMessage(), null, err.getDescriptiveMessage());
    }
}
