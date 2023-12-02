package com.example.inz.customer.operation.dto;

import jakarta.persistence.*;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SignUpDto {
    private String name;
    private String surname;
    private String login;
    private String password;
}
