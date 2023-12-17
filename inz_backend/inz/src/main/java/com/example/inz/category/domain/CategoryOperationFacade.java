package com.example.inz.category.domain;

import com.example.inz.category.dto.CategoryDto;
import com.example.inz.customer.operation.dto.UserLoginDto;
import com.example.inz.customer.operation.domain.Customer;
import com.example.inz.customer.operation.domain.CustomerRepository;
import com.example.inz.customer.operation.exception.UserNotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryOperationFacade {

    CategoryRepository categoryRepository;
    CustomerRepository customerRepository;

    @Autowired
    public CategoryOperationFacade(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    public CategoryDto saveCategory(CategoryDto categoryDto) {
        Optional<Category> optionalCategory = categoryRepository.findByName(categoryDto.getName());

        Optional<Customer> customerId = customerRepository.findByLogin(categoryDto.getUser());
        if (customerId.isEmpty()) {
            throw new UserNotFoundException("Error with user", HttpStatus.BAD_REQUEST);
        }

        Category category = Category.builder()
                .name(categoryDto.getName())
                .user(customerId.get())
                .build();

        Category savedCategory = categoryRepository.save(category);

        return CategoryDto.builder().id(savedCategory.getId()).name(savedCategory.getName()).build();
    }

    public List<CategoryDto> getCategoryByUser(UserLoginDto user) {
        Optional<Customer> customerId = customerRepository.findByLogin(user.getLogin());
        if (customerId.isEmpty()) {
            throw new UserNotFoundException("Error with user", HttpStatus.BAD_REQUEST);
        }
        List<Category> category = categoryRepository.getCategoriesByUserId(customerId.get().getId());

        List<CategoryDto> categoryListDto = category.stream()
                .map(this::mapToDto)
                .toList();

        return categoryListDto;
    }

    private CategoryDto mapToDto(Category category) {
        return CategoryDto.builder()
               .name(category.getName())
               .user(category.getUser().getLogin())
               .id(category.getId())
               .build();
    }
}
