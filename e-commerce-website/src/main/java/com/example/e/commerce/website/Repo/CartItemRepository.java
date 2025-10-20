package com.example.e.commerce.website.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.e.commerce.website.Model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long>{
    
}
