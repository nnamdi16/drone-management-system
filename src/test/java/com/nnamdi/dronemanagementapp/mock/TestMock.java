package com.nnamdi.dronemanagementapp.mock;

import com.nnamdi.dronemanagementapp.dto.Response;
import com.nnamdi.dronemanagementapp.dto.ResponseCodes;
import com.nnamdi.dronemanagementapp.model.Drone;
import com.nnamdi.dronemanagementapp.request.CoordinatesDto;
import com.nnamdi.dronemanagementapp.request.DroneDto;
import com.nnamdi.dronemanagementapp.util.ConstantsUtil;
import com.nnamdi.dronemanagementapp.util.Direction;

import java.time.ZonedDateTime;

public class TestMock {
    public static  final String ID = "8D19B947443D4C1BB2700337527BC251";

    public static DroneDto registerDroneDto() {
        DroneDto droneDto = DroneDto.builder()
                .direction(Direction.WEST)
                .build();
        droneDto.setCoordinateX(coordinatesDto().getCoordinateX());
        droneDto.setCoordinateY(coordinatesDto().getCoordinateY());
        return droneDto;
    }

    public static CoordinatesDto coordinatesDto() {
        return CoordinatesDto.coordinatesBuilder()
                .coordinateY(50)
                .coordinateX(4)
                .build();
    }

    public static Drone buildDrone() {
        Drone drone = Drone.builder()
                .direction(Direction.WEST)
                .coordinateX(coordinatesDto().getCoordinateX())
                .coordinateY(coordinatesDto().getCoordinateY())
                .build();
        drone.setId(ID);
        drone.setCreatedDate(ZonedDateTime.now());
        drone.setLastModifiedDate(ZonedDateTime.now());
        drone.setCreatedBy("John Doe");
        drone.setLastModifiedBy("John Doe");

        return  drone;
    }

    public static Response buildResponse (Object data) {
        return new Response(ResponseCodes.SUCCESS.code(), ConstantsUtil.SUCCESSFUL, data, null);
    }
}
