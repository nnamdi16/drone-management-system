package com.nnamdi.dronemanagementapp.service.impl;

import com.nnamdi.dronemanagementapp.dto.DroneDto;
import com.nnamdi.dronemanagementapp.exception.BadRequestException;
import com.nnamdi.dronemanagementapp.exception.ModelAlreadyExistException;
import com.nnamdi.dronemanagementapp.exception.NotFoundException;
import com.nnamdi.dronemanagementapp.model.Drone;
import com.nnamdi.dronemanagementapp.provider.MessageProvider;
import com.nnamdi.dronemanagementapp.repository.DroneRepository;
import com.nnamdi.dronemanagementapp.request.RegisterDroneDto;
import com.nnamdi.dronemanagementapp.request.UpdateDronePositionDto;
import com.nnamdi.dronemanagementapp.service.DroneService;
import com.nnamdi.dronemanagementapp.util.AppUtil;
import com.nnamdi.dronemanagementapp.util.DroneUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {
    private final ModelMapper modelMapper;
    private final DroneUtil droneUtil;
    private final DroneRepository droneRepository;
    private final MessageProvider messageProvider;
    private final ExternalApiService apiService;


    @Override
    public DroneDto registerDrone(RegisterDroneDto droneDto) {
        Optional<Drone> existingDrone = droneRepository.findByCoordinatesOrName(droneDto.getCoordinateX(), droneDto.getCoordinateY(), droneDto.getName());
        if (existingDrone.isPresent()) {
            throw new ModelAlreadyExistException(messageProvider.getDroneAlreadyExist(String.valueOf(droneDto.getCoordinateX()), String.valueOf(droneDto.getCoordinateY()), droneDto.getName()));
        }
        log.info("about to register a drone");
        Drone drone = droneUtil.buildDroneEntity(droneDto);
        final var registeredDrone = droneRepository.save(drone);
        return modelMapper.map(registeredDrone, DroneDto.class);

    }

    @Override
    public DroneDto moveDrone(String id, UpdateDronePositionDto newPosition) {
        log.info("About to change drone direction");

        Drone currentDrone = droneRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(messageProvider.getDroneNotFound(id)));

        if (!droneUtil.isValidDirectionChange(currentDrone, newPosition)) {
            throw new BadRequestException(messageProvider.getInvalidDirection(
                    String.valueOf(currentDrone.getDirection()),
                    String.valueOf(newPosition.getDirection())));
        }

        currentDrone.setDirection(newPosition.getDirection());
        currentDrone.setCoordinateX(newPosition.getCoordinateX());
        currentDrone.setCoordinateY(newPosition.getCoordinateY());

        Drone updatedDrone = droneRepository.save(currentDrone);
        return modelMapper.map(updatedDrone, DroneDto.class);
    }

    @Override
    public DroneDto getDronePosition(String id) {
        Optional<Drone> optionalDrone = droneRepository.findById(id);
        if (optionalDrone.isPresent()) {
            return modelMapper.map(optionalDrone.get(), DroneDto.class);
        } else {
            throw  new NotFoundException(messageProvider.getDroneNotFound(id));
        }
    }

    @Override
    public PageImpl<DroneDto> getDrones(int page, int limit) {
        log.info("about to retrieve all notes by pagination {}, {}", page, limit);
        AppUtil.validatePageRequest(page, limit);
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Drone> drones = droneRepository.findAll(pageable);
//       return apiService.getDrones(page, limit);
        List<DroneDto> droneDto = drones.getContent().stream().map(note -> modelMapper.map(note, DroneDto.class)).toList();
        return new PageImpl<>(droneDto, drones.getPageable(), drones.getTotalElements());
    }


}
