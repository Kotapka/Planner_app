package com.example.inz.customer.operation.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByLogin(String login);

    @Query("SELECT c FROM Customer c WHERE c.login = :login2")
    Optional<Customer> findByLoginCat(@Param("login2") String login);
}
