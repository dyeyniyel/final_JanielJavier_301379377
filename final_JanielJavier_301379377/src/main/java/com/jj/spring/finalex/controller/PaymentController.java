//package com.jj.spring.finalex.controller;
//
//import com.jj.spring.finalex.model.Payment;
//import com.jj.spring.finalex.service.PaymentService;
//import com.jj.spring.finalex.service.ReservationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//public class PaymentController {
//
//    @Autowired
//    private PaymentService paymentService;
//    
//    @Autowired
//    private ReservationService reservationService;
//
//    //#display payment form with reservation id passed as query parameter
//    @GetMapping("/paymentForm")
//    public String showPaymentForm(@RequestParam("reservationId") String reservationId, Model model) {
//        Payment payment = new Payment();
//        model.addAttribute("payment", payment);
//        model.addAttribute("reservationId", reservationId);
//        return "paymentForm";
//    }
//
//    //#handle payment submission, update reservation with payment id and redirect to final confirmation
//    @PostMapping("/payment")
//    public String handlePaymentSubmission(@RequestParam("reservationId") String reservationId,
//                                          @ModelAttribute("payment") Payment payment) {
//        Payment savedPayment = paymentService.add(payment);
//        //#update reservation with the saved payment id
//        reservationService.updateReservationWithPayment(reservationId, savedPayment.getId());
//        //#redirect to final confirmation page
//        return "redirect:/finalConfirmation?reservationId=" + reservationId;
//    }
//}

package com.jj.spring.finalex.controller;

import com.jj.spring.finalex.model.Payment;
import com.jj.spring.finalex.model.Reservation;
import com.jj.spring.finalex.service.PaymentService;
import com.jj.spring.finalex.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private ReservationService reservationService;

    //display payment form: get reservation details to calculate the total, set current date
    @GetMapping("/paymentForm")
    public String showPaymentForm(@RequestParam("reservationId") String reservationId, Model model) {
        //get reservation details to determine travel class and number of passengers
        Reservation reservation = reservationService.findById(reservationId);

        //determine base price based on travel class
        double basePrice = 0.0;
        if (reservation.getTravelClass().equalsIgnoreCase("Economy")) {
            basePrice = 100.0;
        } else if (reservation.getTravelClass().equalsIgnoreCase("Business")) {
            basePrice = 200.0;
        } else if (reservation.getTravelClass().equalsIgnoreCase("First")) {
            basePrice = 300.0;
        }
        
        int noOfPassengers = reservation.getNoOfPassengers();
        double subtotal = basePrice * noOfPassengers;
        double tax = subtotal * 0.10;  //#10% tax
        double totalAmount = subtotal + tax;
        
        Payment payment = new Payment();
        payment.setAmount(totalAmount);
        payment.setDate(LocalDate.now());
        
        model.addAttribute("payment", payment);
        model.addAttribute("reservationId", reservationId);
        
        //add details for display in the template
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("basePrice", basePrice);
        model.addAttribute("numberOfPassengers", noOfPassengers);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("tax", tax);
        
        return "paymentForm";
    }


    //handle payment submission, update reservation with payment id
    @PostMapping("/payment")
    public String handlePaymentSubmission(@RequestParam("reservationId") String reservationId,
                                          @ModelAttribute("payment") Payment payment) {
        Payment savedPayment = paymentService.add(payment);
        System.out.println("Payment saved: " + savedPayment.toString());
        reservationService.updateReservationWithPayment(reservationId, savedPayment.getId());
        return "redirect:/finalConfirmation?reservationId=" + reservationId;
    }
}
