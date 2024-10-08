package com.nnamdi.dronemanagementapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.nnamdi.dronemanagementapp.dto.DroneDto;
import com.nnamdi.dronemanagementapp.request.RegisterDroneDto;
import com.nnamdi.dronemanagementapp.request.UpdateDronePositionDto;
import org.springframework.data.domain.PageImpl;

import java.io.IOException;


/**
 * Drone Service
 */
public interface DroneService {
    DroneDto registerDrone(RegisterDroneDto drone);

    DroneDto moveDrone(String id, UpdateDronePositionDto updateDroneDto);

    DroneDto getDronePosition(String id);

    PageImpl<DroneDto> getDrones(int page, int limit);

    JsonNode readFilesContent () throws IOException;
}
