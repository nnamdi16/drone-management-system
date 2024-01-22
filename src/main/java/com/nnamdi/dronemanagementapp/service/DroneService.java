package com.nnamdi.dronemanagementapp.service;

import com.nnamdi.dronemanagementapp.dto.DroneDto;
import com.nnamdi.dronemanagementapp.request.RegisterDroneDto;
import com.nnamdi.dronemanagementapp.request.UpdateDroneDto;


/**
 * Drone Service
 */
public interface DroneService {
    DroneDto registerDrone(RegisterDroneDto drone);
    DroneDto moveDrone(String id, UpdateDroneDto updateDroneDto);
    DroneDto getDronePosition(String id);
}
