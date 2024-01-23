package com.nnamdi.dronemanagementapp.repository;

import com.nnamdi.dronemanagementapp.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<Drone, String> {

    @Query("SELECT d FROM Drone d WHERE d.coordinateX = :coordinateX AND d.coordinateY = :coordinateY OR LOWER(d.name) = LOWER(:name)")
    Optional<Drone> findByCoordinatesOrName(@Param("coordinateX") int coordinateX, @Param("coordinateY") int coordinateY, String name);
}
