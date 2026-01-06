package com.example.flightreservations.controller;

import com.example.flightreservations.entity.Reservation;
import com.example.flightreservations.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @GetMapping("/availability/{flightNumber}")
    public ResponseEntity<Map<String, Object>> checkSeatAvailability(@PathVariable String flightNumber) {
        List<Integer> occupiedSeats = reservationService.getOccupiedSeats(flightNumber);
        return ResponseEntity.ok(Map.of(
            "flightNumber", flightNumber,
            "occupiedSeats", occupiedSeats,
            "totalOccupied", occupiedSeats.size()
        ));
    }
}