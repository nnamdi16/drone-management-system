package com.nnamdi.dronemanagementapp.mock;

import com.nnamdi.dronemanagementapp.dto.DroneDto;
import com.nnamdi.dronemanagementapp.dto.Response;
import com.nnamdi.dronemanagementapp.dto.ResponseCodes;
import com.nnamdi.dronemanagementapp.model.Drone;
import com.nnamdi.dronemanagementapp.request.RegisterDroneDto;
import com.nnamdi.dronemanagementapp.request.UpdateDronePositionDto;
import com.nnamdi.dronemanagementapp.util.ConstantsUtil;
import com.nnamdi.dronemanagementapp.util.Direction;

import java.time.ZonedDateTime;

public class TestMock {
    public static  final String ID = "8D19B947443D4C1BB2700337527BC251";
    public static final String DRONE_NAME = "DRONE";

    public static RegisterDroneDto registerDroneDto() {
        return RegisterDroneDto.builder()
                .direction(Direction.WEST)
                .coordinateX(9)
                .coordinateY(4)
                .name(DRONE_NAME)
                .build();
    }

    public static RegisterDroneDto registerDroneBadRequestDto() {
        return RegisterDroneDto.builder()
                .direction(Direction.WEST)
                .name(DRONE_NAME)
                .coordinateX(-10)
                .coordinateY(10)
                .build();
    }


    public static DroneDto buildDroneDto() {
        return DroneDto.builder()
                .direction(Direction.WEST)
                .coordinateX(10)
                .coordinateY(10)
                .name(DRONE_NAME)
                .id(ID)
                .build();
    }




    public static DroneDto buildDroneDto(UpdateDronePositionDto updateDronePositionDto) {
        return DroneDto.builder()
                .direction(updateDronePositionDto.getDirection())
                .coordinateX(updateDronePositionDto.getCoordinateX())
                .coordinateY(updateDronePositionDto.getCoordinateY())
                .name(DRONE_NAME)
                .id(ID)
                .build();
    }


    public static Drone buildDrone(RegisterDroneDto droneDto) {
        Drone drone = Drone.builder()
                .direction(droneDto.getDirection())
                .coordinateX(droneDto.getCoordinateX())
                .coordinateY(droneDto.getCoordinateY())
                .build();
        drone.setId(ID);
        drone.setCreatedDate(ZonedDateTime.now());
        drone.setLastModifiedDate(ZonedDateTime.now());
        return  drone;
    }

    public static Drone updateDrone(UpdateDronePositionDto droneDto) {
        RegisterDroneDto registerDroneDto = registerDroneDto();
        Drone drone = buildDrone(registerDroneDto);
        drone.setDirection(droneDto.getDirection());
        drone.setCoordinateY(droneDto.getCoordinateY());
        drone.setCoordinateX(droneDto.getCoordinateX());
        drone.setLastModifiedDate(ZonedDateTime.now());
        return drone;
    }

    public static Response buildResponse (Object data) {
        return new Response(ResponseCodes.SUCCESS.code(), ConstantsUtil.SUCCESSFUL, data, null);
    }


    public static UpdateDronePositionDto updateDronePositionDto() {
        return UpdateDronePositionDto
                .builder()
                .coordinateX(9)
                .coordinateY(8)
                .direction(Direction.WEST)
                .build();
    }
}
