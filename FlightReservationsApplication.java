package com.example.flightreservations;

import com.example.flightreservations.entity.Reservation;
import com.example.flightreservations.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.time.LocalDateTime;

@SpringBootApplication
public class FlightReservationsApplication implements CommandLineRunner {
    
    @Autowired
    private ReservationRepository reservationRepository;
    
    public static void main(String[] args) {
        SpringApplication.run(FlightReservationsApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        if (reservationRepository.count() == 0) {
            reservationRepository.save(new Reservation("AA101", "Juan Pérez", "juan.perez@email.com", 
                LocalDateTime.of(2024, 3, 15, 10, 30), 12));
            
            reservationRepository.save(new Reservation("AA101", "María García", "maria.garcia@email.com", 
                LocalDateTime.of(2024, 3, 15, 10, 30), 15));
            
            reservationRepository.save(new Reservation("BB202", "Carlos López", "carlos.lopez@email.com", 
                LocalDateTime.of(2024, 3, 16, 14, 45), 8));
            
            reservationRepository.save(new Reservation("CC303", "Ana Martínez", "ana.martinez@email.com", 
                LocalDateTime.of(2024, 3, 17, 9, 15), 22));
            
            reservationRepository.save(new Reservation("DD404", "Luis Rodríguez", "luis.rodriguez@email.com", 
                LocalDateTime.of(2024, 3, 18, 16, 20), 5));
        }
    }
}