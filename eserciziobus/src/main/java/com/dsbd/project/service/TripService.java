package com.dsbd.project.service;

import com.dsbd.project.entity.Trip;
import com.dsbd.project.entity.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    // Recupera tutte le corse con filtri opzionali
    public List<Trip> getAllTrips(String origin, String destination) {
        if (origin != null && destination != null) {
            return tripRepository.findByOriginAndDestination(origin, destination);
        } else if (origin != null) {
            return tripRepository.findByOrigin(origin);
        } else if (destination != null) {
            return tripRepository.findByDestination(destination);
        } else {
            return tripRepository.findAll();
        }
    }

    // Recupera una singola corsa per ID
    public Trip getTripById(Integer tripId) {
        return tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found with ID: " + tripId));
    }

    // Crea una nuova corsa
    @Transactional
    public Trip addTrip(Trip trip) {
        if (trip.getPrice() == null) trip.setPrice(trip.getPrice() == null ? null : trip.getPrice());
        return tripRepository.save(trip);
    }
}
