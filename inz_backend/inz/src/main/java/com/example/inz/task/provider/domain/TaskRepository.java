package com.example.inz.task.provider.domain;

import com.example.inz.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByName(String name);

    @Query("SELECT c FROM Task c WHERE c.user.id = :userId")
    List<Task> getTaskByUserId(@Param("userId") Long userId);

    @Query("SELECT c FROM Task c WHERE c.user.id = :userId AND c.category.id = :categoryId")
    List<Task> getTaskByUserIdAndCategory(@Param("userId") Long userId,@Param("categoryId") Long categoryId);

}
