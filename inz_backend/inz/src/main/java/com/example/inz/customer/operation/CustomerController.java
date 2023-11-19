package com.example.inz.customer.operation;

import com.example.inz.customer.operation.domain.Customer;
import com.example.inz.customer.operation.domain.CustomerOperationFacade;
import com.example.inz.operations.MD5;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/vi/customer")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerController {

    private final CustomerOperationFacade customerOperationFacade;

    @Autowired
    public CustomerController(CustomerOperationFacade customerOperationFacade) {
        this.customerOperationFacade = customerOperationFacade;
    }

    //For tests
    @GetMapping(value = "/getAll", produces = "application/json")
    public List<Customer> getCustomers() {
        return customerOperationFacade.getCustomers();
    }

    @PostMapping(value = "/saveNewCustomer", produces = "application/json")
    @Operation(summary = "Saving new user to database")
    public Customer saveNewCustomer(@RequestBody Customer customer) {
        customerOperationFacade.createNewCustomer(Customer.builder()
                        .login(customer.getLogin())
                        .name(customer.getName())
                        .surname(customer.getSurname())
                        .password(MD5.getMd5(customer.getPassword()))
                        .build());
        return customer;
    }
}
