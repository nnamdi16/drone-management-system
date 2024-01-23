package com.nnamdi.dronemanagementapp.util;

import com.nnamdi.dronemanagementapp.model.Drone;
import com.nnamdi.dronemanagementapp.request.RegisterDroneDto;
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
        return   modelMapper.map(droneDto, Drone.class);
    }

    public boolean isValidDirectionChange(Direction currentDirection, Direction updatedDirection) {
        return switch (currentDirection) {
            case NORTH, SOUTH -> updatedDirection == Direction.EAST || updatedDirection == Direction.WEST;
            case EAST, WEST -> updatedDirection == Direction.NORTH || updatedDirection == Direction.SOUTH;
        };
    }
}
