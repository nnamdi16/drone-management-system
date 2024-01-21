package com.nnamdi.dronemanagementapp.service;

import com.nnamdi.dronemanagementapp.dto.Response;
import com.nnamdi.dronemanagementapp.request.DroneDto;
import com.nnamdi.dronemanagementapp.service.impl.DroneServiceImpl;
import com.nnamdi.dronemanagementapp.util.ResponseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.test.StepVerifier;

import static com.nnamdi.dronemanagementapp.mock.TestMock.buildResponse;
import static com.nnamdi.dronemanagementapp.mock.TestMock.registerDroneDto;
import static org.mockito.Mockito.when;


class DroneServiceTest {

    @Mock
    ResponseUtil responseUtil;


    @Mock
    private  DroneService droneService;


    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        droneService = new DroneServiceImpl(responseUtil);
    }

    @Test
    void registerDrone() {
        DroneDto drone = registerDroneDto();

        Response response = buildResponse(drone);
        when(responseUtil.getSuccessResponse(drone)).thenReturn(response);
        StepVerifier.create(droneService.registerDrone(drone)).expectNext(response).verifyComplete();
    }


}
