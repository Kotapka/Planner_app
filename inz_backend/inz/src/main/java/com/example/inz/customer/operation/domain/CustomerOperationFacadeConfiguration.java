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

    @Autowired
    CustomerOperationFacadeConfiguration(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Bean
    CustomerOperationFacade customerOperationFacade(){
        return customerOperationFacade(customerRepository);
    }

    static CustomerOperationFacade customerOperationFacade(CustomerRepository customerRepository){
        return new CustomerOperationFacade(customerRepository);
    }

}
