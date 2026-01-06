package com.example.flightreservations;

import com.example.flightreservations.config.DataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlightReservationsApplication implements CommandLineRunner {
    
    @Autowired
    private DataInitializer dataInitializer;
    
    public static void main(String[] args) {
        SpringApplication.run(FlightReservationsApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        dataInitializer.initializeData();
    }
}