package com.example.e.commerce.website.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.e.commerce.website.Model.Order;
import com.example.e.commerce.website.Model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long>{

    Payment findByOrder(Order order);
    
}
