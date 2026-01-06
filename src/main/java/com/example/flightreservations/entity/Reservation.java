package com.example.flightreservations.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {
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