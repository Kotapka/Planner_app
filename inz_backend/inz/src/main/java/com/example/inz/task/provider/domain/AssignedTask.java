package com.example.inz.task.provider.domain;

import com.example.inz.category.domain.Category;
import com.example.inz.customer.operation.domain.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity(name = "Assigned_task")
@Table
public class AssignedTask {
    @Id
    @SequenceGenerator(
            name = "assigned_task_sequence",
            sequenceName = "assigned_task_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "assigned_task_sequence"
    )
    private Long id;
    private String name;
    private boolean isActive;
    private Date startDate;
    private Date endDate;
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Customer user;
}
