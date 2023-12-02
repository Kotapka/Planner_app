package com.example.inz.customer.operation.domain;

import com.example.inz.customer.operation.dto.CustomerDto;
import com.example.inz.customer.operation.dto.SignUpDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDto toCustomerDto(Customer customer);

    @Mapping(target = "password", ignore = true)
    Customer signUpToUser(SignUpDto signUpDto);
}
