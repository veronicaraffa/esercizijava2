package com.dsbd.project.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Integer> {

    List<Trip> findByOrigin(String origin);

    List<Trip> findByDestination(String destination);

    List<Trip> findByOriginAndDestination(String origin, String destination);

    // Opzione alternativa con @Query
    @Query("SELECT t FROM Trip t WHERE (:origin IS NULL OR t.origin = :origin) " +
            "AND (:destination IS NULL OR t.destination = :destination)")
    List<Trip> findByOriginAndDestinationOptional(@Param("origin") String origin,
                                                  @Param("destination") String destination);
}
