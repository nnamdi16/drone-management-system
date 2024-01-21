package com.nnamdi.dronemanagementapp.service.impl;

import com.nnamdi.dronemanagementapp.dto.Response;
import com.nnamdi.dronemanagementapp.model.Drone;
import com.nnamdi.dronemanagementapp.request.DroneDto;
import com.nnamdi.dronemanagementapp.request.UpdateDroneDto;
import com.nnamdi.dronemanagementapp.service.DroneService;
import com.nnamdi.dronemanagementapp.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public class DroneServiceImpl implements DroneService {
    private final ResponseUtil responseUtil;

    @Autowired
    public DroneServiceImpl(ResponseUtil responseUtil) {
        this.responseUtil = responseUtil;
    }

    @Override
    public Mono<Response> registerDrone(DroneDto drone) {
        return null;
    }

    @Override
    public Mono<Drone> moveDrone(String id, UpdateDroneDto updateDroneDto) {
        return null;
    }

    @Override
    public Mono<Drone> getDronePosition(String id) {
        return null;
    }
}
