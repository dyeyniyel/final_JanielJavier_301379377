package com.jj.spring.finalex.service;

import com.jj.spring.finalex.model.Payment;
import com.jj.spring.finalex.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    
    //save payment record 
    public Payment add(Payment payment) {
        return paymentRepository.save(payment);
    }
    
    //update payment record if needed without recalculation
    public Payment update(Payment payment) {
        if (payment.getId() == null || !paymentRepository.existsById(payment.getId())) {
            throw new RuntimeException("payment not found for update");
        }
        return paymentRepository.save(payment);
    }
    
    //find all payments
    public List<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }
    
    //find a specific payment by id
    public Payment findPaymentById(String id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }
}
