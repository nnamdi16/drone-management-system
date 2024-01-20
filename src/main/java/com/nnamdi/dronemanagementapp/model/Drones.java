package com.nnamdi.dronemanagementapp.model;

import com.nnamdi.dronemanagementapp.util.Direction;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "drones")
public class Drones extends AbstractEntity implements Serializable {
    @Column(name = "coordinateX")
    private int coordinateX;

    @Column(name = "coordinateY")
    private int coordinateY;

    @Column(name = "direction")
    private Direction direction;
}
