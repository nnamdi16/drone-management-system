package com.nnamdi.dronemanagementapp.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nnamdi.dronemanagementapp.util.Direction;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDronePositionDto  implements Serializable {
    @NotNull(message = "x-coordinate must be provided")
    @JsonProperty("x-coordinate")
    @Max(value = 9, message = "x co-ordinate field boundary must not exceed 10")
    @Min(value = 0, message = "x co-ordinate field boundary must not be below 0")
    private int coordinateX;

    @NotNull(message = "y-coordinate must be provided")
    @Max(value = 9, message = "y co-ordinate field boundary must not exceed 10")
    @Min(value = 0, message = "y co-ordinate field boundary must not be below 0")
    @JsonProperty("y-coordinate")
    private int coordinateY;

    @NotNull(message = "direction must be provided")
    @Enumerated(EnumType.STRING)
    private Direction direction;
}