package com.example.e.commerce.website.Repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.e.commerce.website.Model.Order;
import com.example.e.commerce.website.Model.OrderStatus;
import com.example.e.commerce.website.Model.signUp;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>{

    Order findByIdAndUser(Long orderId, signUp user);

    List<Order> findByUserOrderByOrderDateDesc(signUp user);

   

    long countByStatus(OrderStatus status);

   

    long countByOrderDateAfter(LocalDateTime weekAgo);
     Page<Order> findAll(Specification<Order> spec, Pageable pageable);
    
    // Additional methods
   
    
    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status = :status")
    Double sumTotalAmountByStatus(@Param("status") OrderStatus status);
    
    List<Order> findByUser(signUp user);
    List<Order> findByStatus(OrderStatus status);

    List<Order> findAll(Specification<Order> spec);
}