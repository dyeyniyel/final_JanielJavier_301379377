package com.jj.spring.finalex.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jj.spring.finalex.model.Payment;
import com.jj.spring.finalex.model.Reservation;
import com.jj.spring.finalex.service.PaymentService;
import com.jj.spring.finalex.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentRestController {

    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private ReservationService reservationService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    //POST endpoint that accepts reservationId as a path variable
    @PostMapping("/{reservationId}")
    public Payment addPayment(@PathVariable String reservationId, @RequestBody Payment payment) {
        //Retrieve reservation details to compute payment
        Reservation reservation = reservationService.findById(reservationId);
        
        //Auto-calculate the amount using travel class and number of passengers
        double computedAmount = payment.calculate(reservation.getTravelClass(), reservation.getNoOfPassengers());
        payment.setAmount(computedAmount);
        payment.setDate(LocalDate.now());
        
        Payment savedPayment = paymentService.add(payment);
        reservationService.updateReservationWithPayment(reservationId, savedPayment.getId());
        
        try {
            //explicitly convert the saved payment object to a JSON string
            String paymentJson = objectMapper.writeValueAsString(savedPayment);
            System.out.println("Saved Payment JSON: " + paymentJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return savedPayment;
    }
    
    
    //PUT endpoint to update an existing payment
    @PutMapping
    public Payment updatePayment(@RequestBody Payment payment) {
        return paymentService.update(payment);
    }
    
    //GET endpoint to retrieve all payments
    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.findAllPayments();
    }
    
    //GET endpoint to retrieve a specific payment by id
    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable String id) {
        return paymentService.findPaymentById(id);
    }
}
