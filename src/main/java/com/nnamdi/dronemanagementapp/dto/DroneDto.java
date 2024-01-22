package com.nnamdi.dronemanagementapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nnamdi.dronemanagementapp.util.Direction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DroneDto implements Serializable {

    private String id;

    @JsonProperty("x-coordinate")
    private int coordinateX;

    @JsonProperty("y-coordinate")
    private int coordinateY;

    private String name;

    private Direction direction;

}
