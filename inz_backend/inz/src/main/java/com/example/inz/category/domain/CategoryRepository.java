package com.example.inz.category.domain;

import com.example.inz.category.dto.CategoryDto;
import com.example.inz.customer.operation.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    @Query("SELECT c FROM Category c WHERE c.user.id = :userId")
    List<Category> getCategoriesByUserId(@Param("userId") Long userId);


}
