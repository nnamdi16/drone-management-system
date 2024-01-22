package com.nnamdi.dronemanagementapp.controller;

import com.nnamdi.dronemanagementapp.dto.Response;
import com.nnamdi.dronemanagementapp.request.RegisterDroneDto;
import com.nnamdi.dronemanagementapp.service.DroneService;
import com.nnamdi.dronemanagementapp.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.nnamdi.dronemanagementapp.controller.BaseApiController.BASE_API_PATH;
import static com.nnamdi.dronemanagementapp.controller.BaseApiController.DRONE;

@RestController
@Slf4j
@RequestMapping(BASE_API_PATH + DRONE)
@RequiredArgsConstructor
public class DroneController {
    private final DroneService droneService;
    private final ResponseUtil responseUtil;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Response> registerDrone(@RequestBody @Valid RegisterDroneDto requestDto) {
        Response  response =  responseUtil.getSuccessResponse(droneService.registerDrone(requestDto));
        return ResponseEntity.ok(response);
    }
}
