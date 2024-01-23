package com.nnamdi.dronemanagementapp.service.impl;

import com.nnamdi.dronemanagementapp.dto.DroneDto;
import com.nnamdi.dronemanagementapp.exception.BadRequestException;
import com.nnamdi.dronemanagementapp.exception.ModelAlreadyExistException;
import com.nnamdi.dronemanagementapp.exception.NotFoundException;
import com.nnamdi.dronemanagementapp.model.Drone;
import com.nnamdi.dronemanagementapp.repository.DroneRepository;
import com.nnamdi.dronemanagementapp.request.RegisterDroneDto;
import com.nnamdi.dronemanagementapp.request.UpdateDronePositionDto;
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
        Optional<Drone> existingDrone = droneRepository.findByCoordinatesOrName(droneDto.getCoordinateX(), droneDto.getCoordinateY(), droneDto.getName());
        if (existingDrone.isPresent()) {
            throw new ModelAlreadyExistException(ConstantsUtil.ALREADY_EXIST);
        }
        Drone drone = droneUtil.buildDroneEntity(droneDto);
        final var registeredDrone = droneRepository.save(drone);
        return modelMapper.map(registeredDrone, DroneDto.class);

    }

    @Override
    public DroneDto moveDrone(String id, UpdateDronePositionDto updateDroneDto) {
       Optional<Drone> existingDrone = droneRepository.findById(id);
       if (existingDrone.isEmpty()) {
           throw new NotFoundException(ConstantsUtil.NOT_FOUND);
       }

       if (!droneUtil.isValidDirectionChange(existingDrone.get().getDirection(), updateDroneDto.getDirection())) {
           throw  new BadRequestException("Invalid direction from " + existingDrone.get().getDirection() + " to " + updateDroneDto.getDirection());
       }
       existingDrone.get().setDirection(updateDroneDto.getDirection());
       existingDrone.get().setCoordinateX(updateDroneDto.getCoordinateX());
       existingDrone.get().setCoordinateY(updateDroneDto.getCoordinateY());

        final var updatedDrone = droneRepository.save(existingDrone.get());
        return modelMapper.map(updatedDrone, DroneDto.class);

    }

    @Override
    public Optional<Drone> getDronePosition(String id) {
        return droneRepository.findById(id);
    }




}
