package com.example.inz.task.provider.domain;

import com.example.inz.category.domain.CategoryRepository;
import com.example.inz.customer.operation.domain.CustomerRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskProviderFacadeConfiguration {
    TaskRepository taskRepository;
    CategoryRepository categoryRepository;
    CustomerRepository customerRepository;

    @Autowired
    TaskProviderFacadeConfiguration(TaskRepository taskRepository, CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }
    @Bean
    TaskProviderFacade taskProviderFacade(){
        return taskProviderFacade(taskRepository, categoryRepository,customerRepository);
    }

    static TaskProviderFacade taskProviderFacade(TaskRepository taskRepository, CategoryRepository categoryRepository, CustomerRepository customerRepository){
        return new TaskProviderFacade(taskRepository, categoryRepository, customerRepository);
    }
}
