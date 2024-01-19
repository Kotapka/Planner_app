//package com.example.inz.statistics.domain;
//
//import com.example.inz.category.domain.Category;
//import com.example.inz.customer.operation.domain.Customer;
//import com.example.inz.task.provider.domain.Task;
//import jakarta.persistence.*;
//import lombok.*;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Data
//@Entity(name = "Statistics")
//@Table
//public class Statistics {
//    @Id
//    @SequenceGenerator(
//            name = "category_sequence",
//            sequenceName = "category_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "category_sequence"
//    )
//    private Long id;
//
//    private Boolean is_active;
//    private Customer user;
//    private Category category;
//    private Task task;
//    private Long minutes;
//}
