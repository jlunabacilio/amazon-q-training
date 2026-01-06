package com.example.flightreservations.service;

import com.example.flightreservations.entity.Reservation;
import com.example.flightreservations.exception.ReservationNotFoundException;
import com.example.flightreservations.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservación no encontrada con ID: " + id));
    }

    public boolean isSeatAvailable(String flightNumber, Integer seatNumber) {
        return !reservationRepository.existsByFlightNumberAndSeatNumber(flightNumber, seatNumber);
    }

    public List<Integer> getOccupiedSeats(String flightNumber) {
        return reservationRepository.findOccupiedSeatsByFlightNumber(flightNumber);
    }
}