package com.nnamdi.dronemanagementapp.request;

import com.nnamdi.dronemanagementapp.util.Direction;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class DroneDto extends CoordinatesDto implements Serializable {

    @NotBlank(message = "direction must be provided")
    private Direction direction;

}
