package com.example.inz.category;

import com.example.inz.category.domain.CategoryOperationFacade;
import com.example.inz.category.dto.CategoryDto;
import com.example.inz.configuration.UserAuthenticationProvider;
import com.example.inz.customer.operation.domain.CustomerOperationFacade;
import com.example.inz.customer.operation.dto.CustomerDto;
import com.example.inz.customer.operation.dto.LoginDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(path = "/api")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {

    CategoryOperationFacade categoryOperationFacade;

    @Autowired
    public CategoryController(CategoryOperationFacade categoryOperationFacade) {
        this.categoryOperationFacade = categoryOperationFacade;
    }

    @PostMapping(value = "/addCategory", produces = "application/json")
    @Operation(summary = "add new category")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto category = categoryOperationFacade.saveCategory(categoryDto);
        return ResponseEntity.ok(category);
    }

}
