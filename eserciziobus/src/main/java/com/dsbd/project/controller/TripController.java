package com.dsbd.project.controller;

import com.dsbd.project.entity.Trip;
import com.dsbd.project.entity.User;
import com.dsbd.project.exception.InsufficientCreditException;
import com.dsbd.project.service.ProjectUserService;
import com.dsbd.project.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @Autowired
    private ProjectUserService userService;

    // ------------------- ELENCO CORSE -------------------
    @GetMapping
    public ResponseEntity<List<Trip>> getAllTrips(@RequestParam(required = false) String origin,
                                                  @RequestParam(required = false) String destination) {
        List<Trip> trips = tripService.getAllTrips(origin, destination);
        return ResponseEntity.ok(trips);
    }

    // ------------------- CREAZIONE CORSA (ADMIN) -------------------
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Trip> createTrip(@RequestBody Trip trip) {
        Trip created = tripService.addTrip(trip);
        return ResponseEntity.status(201).body(created);
    }

    // ------------------- ACQUISTO CORSA (USER) -------------------
    @PostMapping("/{tripId}/buy")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Map<String, Object>> buyTrip(
            @PathVariable Integer tripId,
            @RequestParam String userEmail // Passiamo l'email dell'utente autenticato
    ) {
        // Ottieni utente
        User user = userService.getUserByEmail(userEmail);

        // Ottieni corsa
        Trip trip = tripService.getTripById(tripId);

        // Controllo credito
        if (user.getCredit().compareTo(trip.getPrice()) < 0) {
            throw new InsufficientCreditException("Credito insufficiente per acquistare la corsa.");
        }

        // Scala il credito
        user.setCredit(user.getCredit().subtract(trip.getPrice()));
        userService.saveUser(user); // Salviamo il nuovo saldo

        // Restituisci ricevuta
        Map<String, Object> receipt = new HashMap<>();
        receipt.put("userId", user.getId());
        receipt.put("tripId", trip.getId());
        receipt.put("addebito", trip.getPrice());
        receipt.put("saldoResiduo", user.getCredit());

        return ResponseEntity.ok(receipt);
    }
}
