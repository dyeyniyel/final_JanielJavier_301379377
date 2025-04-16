package com.jj.spring.finalex.controller;
import com.jj.spring.finalex.model.Customer;
import com.jj.spring.finalex.model.Reservation;
import com.jj.spring.finalex.service.CustomerService;
import com.jj.spring.finalex.service.ReservationService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    
    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public String showReservationForm(Model model) {
        //provide an empty reservation object to the form
        model.addAttribute("reservation", new Reservation());
        return "reservationForm";
    }


    
    @PostMapping("/reservation")
    public String handleReservationSubmission(@Valid @ModelAttribute("reservation") Reservation reservation,
                                              BindingResult result,
                                              Model model) {
        //if there are validation errors, return to the form so that errors can be displayed
        if (result.hasErrors()) {
            return "reservationForm";
        }
        //save reservation using service layer and obtain saved record
        Reservation savedReservation = reservationService.saveReservation(reservation);
        // #log the saved reservation's toString representation on console
        System.out.println("Reservation saved: " + savedReservation.toString());
        //redirect to customer form passing the reservation id as a parameter
        return "redirect:/customerForm?reservationId=" + savedReservation.getId();
    }
    
    //search for reservation by its id
    @GetMapping("/searchReservation")
    public String searchReservation(@RequestParam("reservationId") String reservationId, Model model) {
        try {
            Reservation reservation = reservationService.findById(reservationId);
            //#if found, redirect to dashboard with the reservation id
            return "redirect:/dashboard?reservationId=" + reservation.getId();
        } catch (RuntimeException e) {
            //#if not found, redirect back to the form with an error message (optional)
            return "redirect:/?error=Reservation+not+found";
        }
    }
    

    //display dashboard with reservation and customer details; requires reservationId query parameter
    @GetMapping("/dashboard")
    public String showDashboard(@RequestParam("reservationId") String reservationId, Model model) {
        Reservation reservation = reservationService.findById(reservationId);
        model.addAttribute("reservation", reservation);
        //#if a customer is linked, load its details; otherwise, send an empty customer object
        if (reservation.getCustomerId() != null) {
            Customer customer = customerService.findCustomerById(reservation.getCustomerId());
            model.addAttribute("customer", customer);
        } else {
            Customer customer = new Customer();
            customer.setReservationId(reservationId);
            model.addAttribute("customer", customer);
        }
        return "dashboard";
    }
    

  //handle reservation update from dashboard form
    @PostMapping("/dashboard/updateReservation")
    public String updateReservation(@ModelAttribute("reservation") Reservation reservation) {
        // Get the existing reservation from the DB
        Reservation existingReservation = reservationService.findById(reservation.getId());
        // Preserve customerId and paymentId from the existing record if they are not provided by the form
        if (reservation.getCustomerId() == null || reservation.getCustomerId().isEmpty()) {
            reservation.setCustomerId(existingReservation.getCustomerId());
        }
        if (reservation.getPaymentId() == null || reservation.getPaymentId().isEmpty()) {
            reservation.setPaymentId(existingReservation.getPaymentId());
        }
        // Save the updated reservation
        reservationService.updateReservationDetails(reservation);
        
        Reservation updatedReservation = reservationService.findById(reservation.getId());
        System.out.println("Reservation updated: " + updatedReservation.toString());
        return "redirect:/dashboard?reservationId=" + reservation.getId();
    }

    
 //handle customer update from dashboard form
    @PostMapping("/dashboard/updateCustomer")
    public String updateCustomer(@ModelAttribute("customer") Customer customer) {
        if (customer.getId() == null || customer.getId().isEmpty()) {
            // If no existing customer (id is null), then add a new customer record
            Customer savedCustomer = customerService.add(customer);
            // Update the reservation with the new customer's id
            reservationService.updateReservationWithCustomer(customer.getReservationId(), savedCustomer.getId());
        } else {
            // Else, update the existing customer record
            customerService.update(customer);
            Customer updatedCustomer = customerService.findCustomerById(customer.getId());
            System.out.println("Customer updated: " + updatedCustomer.toString());
        }
        return "redirect:/dashboard?reservationId=" + customer.getReservationId();
    }

    
    //display final confirmation page by loading reservation details (which include linked customer and payment ids)
    @GetMapping("/finalConfirmation")
    public String showFinalConfirmation(@RequestParam("reservationId") String reservationId, Model model) {
        Reservation reservation = reservationService.findById(reservationId);
        model.addAttribute("reservation", reservation);
        return "finalConfirmation";
    }
}
