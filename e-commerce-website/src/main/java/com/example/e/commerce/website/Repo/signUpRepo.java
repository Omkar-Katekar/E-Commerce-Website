package com.example.e.commerce.website.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.e.commerce.website.Model.signUp;

@Repository
public interface signUpRepo extends JpaRepository<signUp,Integer> {

	signUp findByEmail(String email);

	boolean existsByEmail(String email);

}
