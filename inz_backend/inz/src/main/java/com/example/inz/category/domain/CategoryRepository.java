package com.example.inz.category.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    @Query("SELECT c FROM Category c WHERE c.user.id = :userId AND c.name = :categoryName")
    Optional<Category> findByNameAndUser(@Param("userId") Long userId,@Param("categoryName") String name);
    @Query("SELECT c FROM Category c WHERE c.user.id = :userId")
    List<Category> getCategoriesByUserId(@Param("userId") Long userId);


}
