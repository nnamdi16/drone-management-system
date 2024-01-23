package com.nnamdi.dronemanagementapp.util;

import com.nnamdi.dronemanagementapp.model.Drone;
import com.nnamdi.dronemanagementapp.request.RegisterDroneDto;
import com.nnamdi.dronemanagementapp.request.UpdateDronePositionDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DroneUtil {
    private final ModelMapper modelMapper;

    @Autowired
    public DroneUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Drone buildDroneEntity(RegisterDroneDto droneDto) {
        return modelMapper.map(droneDto, Drone.class);
    }

    public boolean isValidDirectionChange(Drone currentDrone, UpdateDronePositionDto newPosition) {
        Direction currentDirection = currentDrone.getDirection();
        int currentCoordinateY = currentDrone.getCoordinateY();
        return (currentDirection != Direction.NORTH || newPosition.getCoordinateY() >= currentCoordinateY) &&
                (currentDirection != Direction.SOUTH || newPosition.getCoordinateY() <= currentCoordinateY);
    }
}
