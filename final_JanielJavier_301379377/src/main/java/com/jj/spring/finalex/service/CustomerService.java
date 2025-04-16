package com.jj.spring.finalex.service;

import com.jj.spring.finalex.model.Customer;
import com.jj.spring.finalex.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    
    //add customer record
    public Customer add(Customer customer) {
        return customerRepository.save(customer);
    }
    
    //update customer record if it exists
    public Customer update(Customer customer) {
        if (customer.getId() == null || !customerRepository.existsById(customer.getId())) {
            throw new RuntimeException("customer not found for update");
        }
        return customerRepository.save(customer);
    }
    
    //find all customers
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }
    
    //find customer by id
    public Customer findCustomerById(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }
}
