package com.nnamdi.dronemanagementapp.service.impl;

import com.nnamdi.dronemanagementapp.dto.DroneDto;
import com.nnamdi.dronemanagementapp.exception.ModelAlreadyExistException;
import com.nnamdi.dronemanagementapp.model.Drone;
import com.nnamdi.dronemanagementapp.repository.DroneRepository;
import com.nnamdi.dronemanagementapp.request.RegisterDroneDto;
import com.nnamdi.dronemanagementapp.request.UpdateDroneDto;
import com.nnamdi.dronemanagementapp.service.DroneService;
import com.nnamdi.dronemanagementapp.util.ConstantsUtil;
import com.nnamdi.dronemanagementapp.util.DroneUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {
    private final ModelMapper modelMapper;
    private final DroneUtil droneUtil;
    private final DroneRepository droneRepository;



    @Override
    public DroneDto registerDrone(RegisterDroneDto droneDto) {
        Optional<Drone> existingDrone = droneRepository.findByCoordinates(droneDto.getCoordinateX(), droneDto.getCoordinateY());
        if (existingDrone.isPresent()) {
            throw new ModelAlreadyExistException(ConstantsUtil.ALREADY_EXIST);
        }
        Drone drone = droneUtil.convertDtoToEntity(droneDto);
        final var registeredDrone = droneRepository.save(drone);
        return modelMapper.map(registeredDrone, DroneDto.class);

    }

    @Override
    public DroneDto moveDrone(String id, UpdateDroneDto updateDroneDto) {
        return null;
    }

    @Override
    public DroneDto getDronePosition(String id) {
        return null;
    }


}
