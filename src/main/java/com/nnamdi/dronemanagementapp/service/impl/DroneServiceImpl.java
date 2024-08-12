package com.nnamdi.dronemanagementapp.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {
    private final ModelMapper modelMapper;
    private final DroneUtil droneUtil;
    private final DroneRepository droneRepository;
    private final MessageProvider messageProvider;
    private static final String DIRECTORY_PATH = "config/";



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


        AppUtil.validatePageRequest(page, limit);
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Drone> drones = droneRepository.findAll(pageable);
        List<DroneDto> droneDto = drones.getContent().stream().map(note -> modelMapper.map(note, DroneDto.class)).toList();
        return new PageImpl<>(droneDto, drones.getPageable(), drones.getTotalElements());
    }

    @Override
    public JsonNode readFilesContent() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultNode = mapper.createObjectNode();

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(DIRECTORY_PATH))) {
            for (Path path : directoryStream) {
                if (Files.isRegularFile(path)) {
                    Properties properties = new Properties();
                    properties.load(Files.newInputStream(path));

                    Map<String, String> map = new HashMap<>();
                    for (String name : properties.stringPropertyNames()) {
                        map.put(name, properties.getProperty(name));
                    }

                    JsonNode fileNode = mapper.convertValue(map, JsonNode.class);
                    resultNode.set(path.getFileName().toString(), fileNode);
                }
            }
        } catch (IOException ex) {
            throw new NotFoundException(messageProvider.fileNotFound(DIRECTORY_PATH));
        }

        return resultNode;
    }




}
