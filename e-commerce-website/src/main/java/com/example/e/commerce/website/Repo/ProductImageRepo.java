package com.example.e.commerce.website.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.e.commerce.website.Model.ProductImage;

@Repository
public interface ProductImageRepo extends JpaRepository<ProductImage,Long> {

}
