package com.nnamdi.dronemanagementapp.service;

import com.nnamdi.dronemanagementapp.dto.DroneDto;
import com.nnamdi.dronemanagementapp.model.Drone;
import com.nnamdi.dronemanagementapp.request.RegisterDroneDto;
import com.nnamdi.dronemanagementapp.request.UpdateDronePositionDto;

import java.util.Optional;


/**
 * Drone Service
 */
public interface DroneService {
    DroneDto registerDrone(RegisterDroneDto drone);
    DroneDto moveDrone(String id, UpdateDronePositionDto updateDroneDto);
    Optional<Drone> getDronePosition(String id);
}
