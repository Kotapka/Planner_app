package com.example.inz.category.domain;

import com.example.inz.customer.operation.domain.CustomerMapper;
import com.example.inz.customer.operation.domain.CustomerOperationFacade;
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

    @Autowired
    CategoryOperationFacadeConfiguration(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Bean
    CategoryOperationFacade categoryOperationFacade(){
        return categoryOperationFacade(categoryRepository);
    }

    static CategoryOperationFacade categoryOperationFacade(CategoryRepository categoryRepository){
        return new CategoryOperationFacade(categoryRepository);
    }
}
