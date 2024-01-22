package com.nnamdi.dronemanagementapp.service;

import com.nnamdi.dronemanagementapp.exception.ModelAlreadyExistException;
import com.nnamdi.dronemanagementapp.model.Drone;
import com.nnamdi.dronemanagementapp.repository.DroneRepository;
import com.nnamdi.dronemanagementapp.request.RegisterDroneDto;
import com.nnamdi.dronemanagementapp.service.impl.DroneServiceImpl;
import com.nnamdi.dronemanagementapp.util.Direction;
import com.nnamdi.dronemanagementapp.util.DroneUtil;
import com.nnamdi.dronemanagementapp.util.ResponseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static com.nnamdi.dronemanagementapp.mock.TestMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class DroneServiceTest {

    @Mock
    ResponseUtil responseUtil;

    @Mock
    private  DroneService droneService;

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private DroneUtil droneUtil;


    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        modelMapper = new ModelMapper();
        droneService = new DroneServiceImpl(modelMapper, droneUtil, droneRepository);
    }

    @Test
    void registerDrone() {
        RegisterDroneDto droneDto = registerDroneDto();
        Drone drone = buildDrone(droneDto);
        when(droneRepository.save(any(Drone.class))).thenReturn(drone);
        when(droneRepository.findByCoordinatesOrName(droneDto.getCoordinateX(), droneDto.getCoordinateY(), droneDto.getName())).thenReturn(Optional.empty());
        when(droneUtil.convertDtoToEntity(droneDto)).thenReturn(drone);
        final var response  = droneService.registerDrone(droneDto);
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
        when(droneUtil.convertDtoToEntity(droneDto)).thenReturn(drone);
        when(droneRepository.findByCoordinatesOrName(droneDto.getCoordinateX(), droneDto.getCoordinateY(), droneDto.getName())).thenReturn(Optional.of(drone));
        assertThatThrownBy(() -> droneService.registerDrone(droneDto)).hasMessage("Entity already exist").isInstanceOf(ModelAlreadyExistException.class);
    }

    @Test
    void getDronePosition() {
        RegisterDroneDto droneDto = registerDroneDto();
        Drone drone = buildDrone(droneDto);
        when(droneRepository.findById(ID)).thenReturn(Optional.of(drone));
        final var response  = droneService.getDronePosition(ID);
        assertThat(response).isNotEmpty();
    }

    @Test
    void getDronePositionWithInvalidID() {
        when(droneRepository.findById(anyString())).thenReturn(Optional.empty());
        final var response  = droneService.getDronePosition(anyString());
        assertThat(response).isEmpty();
    }


}
