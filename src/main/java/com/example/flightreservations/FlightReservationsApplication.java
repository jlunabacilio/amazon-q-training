package com.example.flightreservations;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

@Entity
@Table(name = "reservations")
class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "flight_number")
    private String flightNumber;

    @NotBlank
    @Column(name = "passenger_name")
    private String passengerName;

    @NotBlank
    @Column(name = "passenger_email")
    private String passengerEmail;

    @NotNull
    @Column(name = "departure_date")
    private LocalDateTime departureDate;

    @NotNull
    @Positive
    @Column(name = "seat_number")
    private Integer seatNumber;

    @Column(name = "status")
    private String status = "CONFIRMED";

    public Reservation() {}

    public Reservation(String flightNumber, String passengerName, String passengerEmail, 
                      LocalDateTime departureDate, Integer seatNumber) {
        this.flightNumber = flightNumber;
        this.passengerName = passengerName;
        this.passengerEmail = passengerEmail;
        this.departureDate = departureDate;
        this.seatNumber = seatNumber;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }
    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }
    public String getPassengerEmail() { return passengerEmail; }
    public void setPassengerEmail(String passengerEmail) { this.passengerEmail = passengerEmail; }
    public LocalDateTime getDepartureDate() { return departureDate; }
    public void setDepartureDate(LocalDateTime departureDate) { this.departureDate = departureDate; }
    public Integer getSeatNumber() { return seatNumber; }
    public void setSeatNumber(Integer seatNumber) { this.seatNumber = seatNumber; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

@Repository
interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r.seatNumber FROM Reservation r WHERE r.flightNumber = :flightNumber")
    List<Integer> findOccupiedSeatsByFlightNumber(@Param("flightNumber") String flightNumber);
    boolean existsByFlightNumberAndSeatNumber(String flightNumber, Integer seatNumber);
}

@Service
class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservación no encontrada con ID: " + id));
    }

    public List<Integer> getOccupiedSeats(String flightNumber) {
        return reservationRepository.findOccupiedSeatsByFlightNumber(flightNumber);
    }
}

@RestController
@RequestMapping("/api/v1/reservations")
class ReservationController {
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

class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(String message) {
        super(message);
    }
}

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleReservationNotFound(ReservationNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
            "timestamp", LocalDateTime.now(),
            "status", HttpStatus.NOT_FOUND.value(),
            "error", "Not Found",
            "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
            "timestamp", LocalDateTime.now(),
            "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "error", "Internal Server Error",
            "message", "Error interno del servidor"
        ));
    }
}