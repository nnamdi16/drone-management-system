package com.nnamdi.dronemanagementapp.dto;

public record Response(Integer code, String message, Object data, String descriptiveMessage) {
}
