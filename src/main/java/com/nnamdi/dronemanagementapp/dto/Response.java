package com.nnamdi.dronemanagementapp.dto;

public record Response<T>(Integer code, String message, T data, String descriptiveMessage) {
}
