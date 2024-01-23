package com.nnamdi.dronemanagementapp.service;

import com.nnamdi.dronemanagementapp.dto.DroneDto;
import com.nnamdi.dronemanagementapp.exception.BadRequestException;
import com.nnamdi.dronemanagementapp.exception.ModelAlreadyExistException;
import com.nnamdi.dronemanagementapp.exception.NotFoundException;
import com.nnamdi.dronemanagementapp.model.Drone;
import com.nnamdi.dronemanagementapp.provider.MessageProvider;
import com.nnamdi.dronemanagementapp.repository.DroneRepository;
import com.nnamdi.dronemanagementapp.request.RegisterDroneDto;
import com.nnamdi.dronemanagementapp.request.UpdateDronePositionDto;
import com.nnamdi.dronemanagementapp.service.impl.DroneServiceImpl;
import com.nnamdi.dronemanagementapp.util.Direction;
import com.nnamdi.dronemanagementapp.util.DroneUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static com.nnamdi.dronemanagementapp.mock.TestMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class DroneServiceTest {


    @Mock
    MessageSource messageSource;
    @Mock
    private DroneService droneService;
    @Mock
    private DroneRepository droneRepository;
    @Mock
    private MessageProvider messageProvider;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private DroneUtil droneUtil;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        modelMapper = new ModelMapper();
        messageProvider = new MessageProvider(messageSource);
        droneService = new DroneServiceImpl(modelMapper, droneUtil, droneRepository, messageProvider);
    }

    @Test
    void registerDrone() {
        RegisterDroneDto droneDto = registerDroneDto();
        Drone drone = buildDrone(droneDto);
        when(droneRepository.save(any(Drone.class))).thenReturn(drone);
        when(droneRepository.findByCoordinatesOrName(droneDto.getCoordinateX(), droneDto.getCoordinateY(), droneDto.getName())).thenReturn(Optional.empty());
        when(droneUtil.buildDroneEntity(droneDto)).thenReturn(drone);
        final var response = droneService.registerDrone(droneDto);
        assertThat(response).isNotNull();
        assertThat(response.getCoordinateX()).isNotNegative();
        assertThat(response.getCoordinateY()).isNotNegative();
        assertThat(response.getCoordinateX()).isLessThanOrEqualTo(10);
        assertThat(response.getCoordinateY()).isLessThanOrEqualTo(10);
        assertThat(response.getDirection()).isIn(Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH);
    }

    @Test
    void registerDroneAlreadyExist() {
        RegisterDroneDto droneDto = registerDroneDto();
        Drone drone = buildDrone(droneDto);
        String message = "drone with either co-ordinate " + droneDto.getCoordinateX() + " and " + drone.getCoordinateY() + " or name " + droneDto.getName() + " already exist";
        when(droneUtil.buildDroneEntity(droneDto)).thenReturn(drone);
        when(droneRepository.findByCoordinatesOrName(droneDto.getCoordinateX(), droneDto.getCoordinateY(), droneDto.getName())).thenReturn(Optional.of(drone));
        when(messageProvider.getDroneAlreadyExist(String.valueOf(droneDto.getCoordinateX()), String.valueOf(droneDto.getCoordinateY()), droneDto.getName())).thenReturn(message);
        assertThatThrownBy(() -> droneService.registerDrone(droneDto)).hasMessage(message).isInstanceOf(ModelAlreadyExistException.class);
    }

    @Test
    void getDronePosition() {
        RegisterDroneDto droneDto = registerDroneDto();
        Drone drone = buildDrone(droneDto);
        when(droneRepository.findById(ID)).thenReturn(Optional.of(drone));
        final var response = droneService.getDronePosition(ID);
        assertThat(response).isNotEmpty();
    }

    @Test
    void getAllDronePositions() {
        int page = 1;
        int limit = 10;

        List<Drone> droneList = List.of(buildDrone(registerDroneDto()));
        Page<Drone> dronePage = new PageImpl<>(droneList, PageRequest.of(0, limit), droneList.size());
        when(droneRepository.findAll(PageRequest.of(0, limit))).thenReturn(dronePage);

        Page<DroneDto> result = droneService.getDrones(page, limit);
        assertEquals(dronePage.getTotalElements(), result.getTotalElements());
        assertEquals(dronePage.getTotalPages(), result.getTotalPages());
        assertEquals(dronePage.getNumber() + 1, result.getNumber() + 1);
    }

    @Test
    void getDronePositionWithInvalidID() {
        when(droneRepository.findById(anyString())).thenReturn(Optional.empty());
        final var response = droneService.getDronePosition(anyString());
        assertThat(response).isEmpty();
    }

    @Test
    void moveDrone() {
        RegisterDroneDto registerDroneDto = registerDroneDto();
        Drone drone = buildDrone(registerDroneDto);
        UpdateDronePositionDto updateDronePositionDto = updateDronePositionDto();
        Drone updateDrone = updateDrone(updateDronePositionDto);
        when(droneRepository.save(any(Drone.class))).thenReturn(updateDrone);
        when(droneRepository.findById(anyString())).thenReturn(Optional.of(drone));
        when(droneUtil.isValidDirectionChange(drone, updateDronePositionDto)).thenReturn(true);
        final var response = droneService.moveDrone(ID, updateDronePositionDto);
        assertThat(response).isNotNull();
        assertThat(response.getCoordinateX()).isNotNegative();
        assertThat(response.getCoordinateY()).isNotNegative();
        assertThat(response.getCoordinateX()).isLessThanOrEqualTo(10);
        assertThat(response.getCoordinateY()).isLessThanOrEqualTo(10);
        assertThat(response.getDirection()).isIn(Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH);
    }


    @Test
    void moveDroneNotFoundException() {

        UpdateDronePositionDto updateDronePositionDto = updateDronePositionDto();
        String message = "drone " + ID + " does not exist";
        when(droneRepository.findById(anyString())).thenReturn(Optional.empty());
        when(messageProvider.getDroneNotFound(ID)).thenReturn(message);
        assertThatThrownBy(() -> droneService.moveDrone(ID, updateDronePositionDto)).hasMessage("drone " + ID + " does not exist").isInstanceOf(NotFoundException.class);
    }


    @Test
    void moveDroneBadRequestSouthToNorth() {
        RegisterDroneDto registerDroneDto = registerDroneDto();
        Drone drone = buildDrone(registerDroneDto);
        drone.setDirection(Direction.SOUTH);
        UpdateDronePositionDto updateDronePositionDto = updateDronePositionDto();
        updateDronePositionDto.setDirection(Direction.NORTH);
        updateDronePositionDto.setCoordinateY(8);
        Drone updateDrone = updateDrone(updateDronePositionDto);
        String message = "drone cannot move directly from " + drone.getDirection() + " to " + updateDronePositionDto.getDirection();
        when(droneUtil.isValidDirectionChange(drone, updateDronePositionDto)).thenReturn(false);
        when(droneRepository.save(any(Drone.class))).thenReturn(updateDrone);
        when(droneRepository.findById(anyString())).thenReturn(Optional.of(drone));
        when(messageProvider.getInvalidDirection(String.valueOf(drone.getDirection()), String.valueOf(updateDrone.getDirection()))).thenReturn(message);


        assertThatThrownBy(() -> droneService.moveDrone(ID, updateDronePositionDto)).hasMessage(message).isInstanceOf(BadRequestException.class);
    }

    @Test
    void moveDroneBadRequestNorthToSouth() {
        RegisterDroneDto registerDroneDto = registerDroneDto();
        Drone drone = buildDrone(registerDroneDto);
        drone.setDirection(Direction.NORTH);
        UpdateDronePositionDto updateDronePositionDto = updateDronePositionDto();
        updateDronePositionDto.setCoordinateY(2);
        updateDronePositionDto.setDirection(Direction.SOUTH);
        Drone updateDrone = updateDrone(updateDronePositionDto);
        String message = "drone cannot move directly from " + drone.getDirection() + " to " + updateDronePositionDto.getDirection();
        when(droneUtil.isValidDirectionChange(drone, updateDronePositionDto)).thenReturn(false);
        when(droneRepository.save(any(Drone.class))).thenReturn(updateDrone);
        when(droneRepository.findById(anyString())).thenReturn(Optional.of(drone));
        when(messageProvider.getInvalidDirection(String.valueOf(drone.getDirection()), String.valueOf(updateDrone.getDirection()))).thenReturn(message);


        assertThatThrownBy(() -> droneService.moveDrone(ID, updateDronePositionDto)).hasMessage(message).isInstanceOf(BadRequestException.class);
    }


}
