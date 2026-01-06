package com.example.flightreservations.repository;

import com.example.flightreservations.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
    @Query("SELECT r.seatNumber FROM Reservation r WHERE r.flightNumber = :flightNumber")
    List<Integer> findOccupiedSeatsByFlightNumber(@Param("flightNumber") String flightNumber);
    
    boolean existsByFlightNumberAndSeatNumber(String flightNumber, Integer seatNumber);
}