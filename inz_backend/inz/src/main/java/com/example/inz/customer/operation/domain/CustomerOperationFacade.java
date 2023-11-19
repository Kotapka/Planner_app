package com.example.inz.customer.operation.domain;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerOperationFacade {

    CustomerRepository customerRepository;
    @Autowired
    public CustomerOperationFacade(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    //Do testów
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public void createNewCustomer(Customer customer){ customerRepository.save(customer); }
}
