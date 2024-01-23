package com.nnamdi.dronemanagementapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nnamdi.dronemanagementapp.dto.DroneDto;
import com.nnamdi.dronemanagementapp.exception.BadRequestException;
import com.nnamdi.dronemanagementapp.exception.ModelAlreadyExistException;
import com.nnamdi.dronemanagementapp.exception.NotFoundException;
import com.nnamdi.dronemanagementapp.mock.TestMock;
import com.nnamdi.dronemanagementapp.model.Drone;
import com.nnamdi.dronemanagementapp.request.RegisterDroneDto;
import com.nnamdi.dronemanagementapp.request.UpdateDronePositionDto;
import com.nnamdi.dronemanagementapp.service.DroneService;
import com.nnamdi.dronemanagementapp.util.ConstantsUtil;
import com.nnamdi.dronemanagementapp.util.ResponseUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.nnamdi.dronemanagementapp.controller.BaseApiController.BASE_API_PATH;
import static com.nnamdi.dronemanagementapp.controller.BaseApiController.DRONE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = DroneController.class)
 class DroneControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    DroneService droneService;

    @MockBean
    ResponseUtil responseUtil;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    Gson gson;

    String registerDroneRequest;

    String updateDroneRequest;

    private final String URL = BASE_API_PATH + DRONE ;

    @BeforeEach
    void setup() throws JsonProcessingException {
        registerDroneRequest = objectMapper.writeValueAsString(TestMock.registerDroneDto());
        updateDroneRequest = objectMapper.writeValueAsString(TestMock.updateDronePositionDto());
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(droneService);
    }

    @Test
    void registerDrone() {
        DroneDto droneDto = droneService.registerDrone(any(RegisterDroneDto.class));
        when(droneDto).thenReturn(TestMock.buildDroneDto());
        when(responseUtil.getSuccessResponse(droneDto)).thenReturn(TestMock.buildResponse(TestMock.buildDroneDto()));
        try {
            mockMvc.perform(
                            post(URL)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(registerDroneRequest)
                    )
                    .andExpect(status().isOk())
                    .andDo(print());
        }catch (Exception e) {
            throw  new RuntimeException(e);
        }
    }

    @Test
    void registerDroneBadRequest() throws JsonProcessingException {
       registerDroneRequest = objectMapper.writeValueAsString(TestMock.registerDroneBadRequestDto());
        try {
            mockMvc.perform(
                            post(URL)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(registerDroneRequest)
                    )
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }catch (Exception e) {
            throw  new RuntimeException(e);
        }
    }


    @Test
    void registerDroneThrowsAlreadyExist() throws JsonProcessingException {
        registerDroneRequest = objectMapper.writeValueAsString(TestMock.registerDroneDto());
        DroneDto droneDto = droneService.registerDrone(any(RegisterDroneDto.class));
        when(droneDto).thenThrow(new ModelAlreadyExistException(ConstantsUtil.ALREADY_EXIST));
        try {
            mockMvc.perform(
                            post(URL)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(registerDroneRequest)
                    )
                    .andExpect(status().isConflict())
                    .andDo(print());
        }catch (Exception e) {
            throw  new RuntimeException(e);
        }
    }

    @Test
    void getDronePosition() {
        Optional<Drone> droneDto = droneService.getDronePosition(TestMock.ID);
        when(responseUtil.getSuccessResponse(droneDto)).thenReturn(TestMock.buildResponse(TestMock.buildDrone(TestMock.registerDroneDto())));
        try {
            mockMvc.perform(
                            get(URL + "/" + TestMock.ID)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print());
        }catch (Exception e) {
            throw  new RuntimeException(e);
        }
    }


    @Test
    void moveDrone() {
        DroneDto droneDto = droneService.moveDrone(anyString(),any(UpdateDronePositionDto.class));
        UpdateDronePositionDto updateDronePositionDto = TestMock.updateDronePositionDto();
        when(droneDto).thenReturn(TestMock.buildDroneDto(updateDronePositionDto));
        when(responseUtil.getSuccessResponse(droneDto)).thenReturn(TestMock.buildResponse(TestMock.buildDroneDto()));
        try {
            mockMvc.perform(
                            put(URL + "/" + TestMock.ID)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(updateDroneRequest)
                    )
                    .andExpect(status().isOk())
                    .andDo(print());
        }catch (Exception e) {
            throw  new RuntimeException(e);
        }
    }

    @Test
    void moveDroneNotFoundException() {
        DroneDto droneDto = droneService.moveDrone(anyString(),any(UpdateDronePositionDto.class));
        when(droneDto).thenThrow(new NotFoundException(ConstantsUtil.NOT_FOUND));
        try {
            mockMvc.perform(
                            put(URL + "/" + TestMock.ID)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(updateDroneRequest)
                    )
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }catch (Exception e) {
            throw  new RuntimeException(e);
        }
    }



    @Test
    void moveDroneBadRequest() {
        DroneDto droneDto = droneService.moveDrone(anyString(),any(UpdateDronePositionDto.class));
        RegisterDroneDto registerDroneDto = TestMock.registerDroneDto();
        Drone drone = TestMock.buildDrone(registerDroneDto);
        UpdateDronePositionDto updateDronePositionDto = TestMock.updateDronePositionDto();
        when(droneDto).thenThrow(new BadRequestException("Invalid direction from " + drone.getDirection() + " to " + updateDronePositionDto.getDirection()));
        try {
            mockMvc.perform(
                            put(URL + "/" + TestMock.ID)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(updateDroneRequest)
                    )
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }catch (Exception e) {
            throw  new RuntimeException(e);
        }
    }


}
