package com.example.inz.category.domain;

import com.example.inz.customer.operation.domain.Customer;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity(name = "Category")
@Table
public class Category {
    @Id
    @SequenceGenerator(
            name = "category_sequence",
            sequenceName = "category_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "category_sequence"
    )
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Customer user;
}
