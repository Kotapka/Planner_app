package com.example.inz.task.provider.domain;

import com.example.inz.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssignedTaskRepository  extends JpaRepository<AssignedTask, Long> {

}
