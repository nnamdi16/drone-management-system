package com.nnamdi.dronemanagementapp.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "coordinatesBuilder")
public class CoordinatesDto implements Serializable {
    @NotBlank(message = "co-ordinateX must be provided")
    @JsonProperty("co-ordinateX")
    private int coordinateX;

    @NotBlank(message = "co-ordinateY must be provided")
    @JsonProperty("co-ordinateY")
    private int coordinateY;
}
