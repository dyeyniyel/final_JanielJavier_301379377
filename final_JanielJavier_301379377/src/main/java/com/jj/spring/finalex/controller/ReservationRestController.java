package com.jj.spring.finalex.controller;

import com.jj.spring.finalex.model.Reservation;
import com.jj.spring.finalex.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationRestController {

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping
    public List<Reservation> getAllReservations() {
    	 //return all reservations in json format
        return reservationRepository.findAll();
    }

    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable String id) {
    	 //return a single reservation by id in json format or throw exception if not found
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
    	 //create new reservation from json in the request body and save to mongodb
        return reservationRepository.save(reservation);
    }


}
