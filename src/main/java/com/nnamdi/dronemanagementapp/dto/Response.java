package com.nnamdi.dronemanagementapp.dto;

import java.util.List;

public record Response(Integer code, String message, Object data, List<Error> errors) {
}
