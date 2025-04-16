package com.jj.spring.finalex.service;

import com.jj.spring.finalex.model.Reservation;
import com.jj.spring.finalex.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    //save reservation using repository
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    //update reservation with customer id
    public Reservation updateReservationWithCustomer(String reservationId, String customerId) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new RuntimeException("reservation not found"));
        reservation.setCustomerId(customerId);
        return reservationRepository.save(reservation);
    }

    //update reservation with payment id
    public Reservation updateReservationWithPayment(String reservationId, String paymentId) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new RuntimeException("reservation not found"));
        reservation.setPaymentId(paymentId);
        return reservationRepository.save(reservation);
    }

    //find reservation by id
    public Reservation findById(String reservationId) {
        return reservationRepository.findById(reservationId)
            .orElseThrow(() -> new RuntimeException("reservation not found"));
    }
    
    //update reservation details using repository
    public Reservation updateReservationDetails(Reservation reservation) {
        if (reservation.getId() == null || !reservationRepository.existsById(reservation.getId())) {
            throw new RuntimeException("reservation not found for update");
        }
        return reservationRepository.save(reservation);
    }

}
