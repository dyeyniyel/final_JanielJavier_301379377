package com.jj.spring.finalex.controller;

import com.jj.spring.finalex.model.Customer;
import com.jj.spring.finalex.service.CustomerService;
import com.jj.spring.finalex.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private ReservationService reservationService;

    //display the customer form with reservation id provided as query parameter
    @GetMapping("/customerForm")
    public String showCustomerForm(@RequestParam("reservationId") String reservationId, Model model) {
        Customer customer = new Customer();
        customer.setReservationId(reservationId);  //link customer with reservation id
        model.addAttribute("customer", customer);
        return "customerForm";
    }

    //handle customer form submission and update reservation with customer id
    @PostMapping("/customer")
    public String handleCustomerSubmission(@ModelAttribute("customer") Customer customer) {
        Customer savedCustomer = customerService.add(customer);
        System.out.println("Customer added: " + savedCustomer.toString());
        //update reservation to link with the saved customer id
        reservationService.updateReservationWithCustomer(customer.getReservationId(), savedCustomer.getId());
        //redirect to payment form using same reservation id
        return "redirect:/paymentForm?reservationId=" + customer.getReservationId();
    }
}
