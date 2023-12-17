package com.example.inz.category.domain;

import com.example.inz.customer.operation.domain.CustomerRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryOperationFacadeConfiguration {

    CategoryRepository categoryRepository;
    CustomerRepository customerRepository;

    @Autowired
    CategoryOperationFacadeConfiguration(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }
    @Bean
    CategoryOperationFacade categoryOperationFacade(){
        return categoryOperationFacade(categoryRepository,customerRepository);
    }

    static CategoryOperationFacade categoryOperationFacade(CategoryRepository categoryRepository,CustomerRepository customerRepository){
        return new CategoryOperationFacade(categoryRepository, customerRepository);
    }
}
