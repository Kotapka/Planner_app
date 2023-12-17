package com.example.inz.task.provider.domain;

import com.example.inz.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AssignedTaskRepository  extends JpaRepository<AssignedTask, Long> {
    @Query("SELECT c FROM Assigned_task c WHERE c.user.id = :userId")
    List<AssignedTask> getAssignedByUserId(@Param("userId") Long userId);

    Optional<AssignedTask> findById(Long id);
}
