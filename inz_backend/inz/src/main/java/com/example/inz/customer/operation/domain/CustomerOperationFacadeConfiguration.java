package com.example.inz.customer.operation.domain;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerOperationFacadeConfiguration {

    CustomerRepository customerRepository;
    CustomerMapper customerMapper;

    @Autowired
    CustomerOperationFacadeConfiguration(CustomerRepository customerRepository, CustomerMapper customerMapper){
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Bean
    CustomerOperationFacade customerOperationFacade(){
        return customerOperationFacade(customerRepository, customerMapper);
    }

    static CustomerOperationFacade customerOperationFacade(CustomerRepository customerRepository, CustomerMapper customerMapper){
        return new CustomerOperationFacade(customerRepository, customerMapper);
    }

}
