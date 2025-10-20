package com.example.e.commerce.website.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.e.commerce.website.Model.Cart;
import com.example.e.commerce.website.Model.signUp;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long>{

    Cart findByUser(signUp user);

}