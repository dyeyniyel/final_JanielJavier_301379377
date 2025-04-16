package com.jj.spring.finalex.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jj.spring.finalex.model.Customer;
import com.jj.spring.finalex.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    //POST endpoint to add a new customer.
    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.add(customer);
        try {
            //explicitly converting savedCustomer to JSON 
            String customerJson = objectMapper.writeValueAsString(savedCustomer);
            System.out.println("Saved Customer JSON: " + customerJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return savedCustomer;
    }
    
    //PUT endpoint to update an existing customer.
    @PutMapping
    public Customer updateCustomer(@RequestBody Customer customer) {
        Customer updatedCustomer = customerService.update(customer);
        try {
            //#explicitly converting updatedCustomer to JSON for logging/demonstration purposes
            String customerJson = objectMapper.writeValueAsString(updatedCustomer);
            System.out.println("Updated Customer JSON: " + customerJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updatedCustomer;
    }
    
    //GET endpoint to retrieve all customers.
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.findAllCustomers();
    }
    
    //GET endpoint to retrieve a customer by id.
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable String id) {
        return customerService.findCustomerById(id);
    }
}
