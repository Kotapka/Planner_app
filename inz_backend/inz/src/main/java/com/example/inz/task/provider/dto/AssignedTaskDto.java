package com.example.inz.task.provider.dto;

import com.example.inz.category.domain.Category;
import com.example.inz.customer.operation.domain.Customer;
import com.example.inz.task.provider.domain.Task;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignedTaskDto {
    private boolean isActive;
    private Date startDate;
    private Date endDate;
    private String description;
    private String category;
    private String user;
    private String task;
}
