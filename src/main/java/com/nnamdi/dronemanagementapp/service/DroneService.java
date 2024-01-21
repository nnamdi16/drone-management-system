package com.nnamdi.dronemanagementapp.service;

import com.nnamdi.dronemanagementapp.dto.Response;
import com.nnamdi.dronemanagementapp.model.Drone;
import com.nnamdi.dronemanagementapp.request.DroneDto;
import com.nnamdi.dronemanagementapp.request.UpdateDroneDto;
import reactor.core.publisher.Mono;

/**
 * Drone Service
 */
public interface DroneService {
    Mono<Response> registerDrone(DroneDto drone);
    Mono<Drone> moveDrone(String id, UpdateDroneDto updateDroneDto);
    Mono<Drone> getDronePosition(String id);
}
