package com.example.inz.customer.operation.domain;

import com.example.inz.customer.operation.dto.CustomerDto;
import com.example.inz.customer.operation.dto.LoginDto;
import com.example.inz.customer.operation.dto.SignUpDto;
import com.example.inz.customer.operation.exception.HttpException;
import com.example.inz.operations.MD5;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerOperationFacade {

    CustomerRepository customerRepository;
    CustomerMapper customerMapper;

    @Autowired
    public CustomerOperationFacade(CustomerRepository customerRepository,CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }
    public CustomerDto findByLogin(String login){
        Customer customer = customerRepository.findByLogin(login).orElseThrow(() -> new HttpException("User not found", HttpStatus.NOT_FOUND));
        return customerMapper.toCustomerDto(customer);
    }

    public CustomerDto login(LoginDto login) {
        Customer customer = customerRepository.findByLogin(login.getLogin())
                .orElseThrow(() -> new HttpException("Unknown user", HttpStatus.NOT_FOUND));

        if (customer.getPassword().equals(MD5.getMd5(login.getPassword()))) {
            return customerMapper.toCustomerDto(customer);
        }
        throw new HttpException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public CustomerDto register(SignUpDto userDto) {
        Optional<Customer> optionalUser = customerRepository.findByLogin(userDto.getLogin());

        if (optionalUser.isPresent()) {
            throw new HttpException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        Customer customer = customerMapper.signUpToUser(userDto);
        customer.setPassword(MD5.getMd5(userDto.getPassword()));

        Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.toCustomerDto(savedCustomer);
    }

}
