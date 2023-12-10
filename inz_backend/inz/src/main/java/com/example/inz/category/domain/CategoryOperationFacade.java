package com.example.inz.category.domain;

import com.example.inz.category.dto.CategoryDto;
import com.example.inz.customer.operation.domain.Customer;
import com.example.inz.customer.operation.domain.CustomerMapper;
import com.example.inz.customer.operation.domain.CustomerRepository;
import com.example.inz.customer.operation.dto.CustomerDto;
import com.example.inz.customer.operation.dto.SignUpDto;
import com.example.inz.customer.operation.exception.UserNotFoundException;
import com.example.inz.operations.MD5;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryOperationFacade {

    CategoryRepository categoryRepository;

    @Autowired
    public CategoryOperationFacade(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDto saveCategory(CategoryDto categoryDto) {
        Optional<Category> optionalCategory = categoryRepository.findByName(categoryDto.getName());

        if (optionalCategory.isPresent()) {
            throw new UserNotFoundException("Category already exists", HttpStatus.BAD_REQUEST);
        }

        Category category = Category.builder()
                .name(categoryDto.getName())
                .build();

        Category savedCategory = categoryRepository.save(category);

        return CategoryDto.builder().id(savedCategory.getId()).name(savedCategory.getName()).build();
    }

}
