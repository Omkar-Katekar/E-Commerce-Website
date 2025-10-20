package com.example.e.commerce.website.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
import com.example.e.commerce.website.Model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product ,Long> {

      List<Product> findByCategory(String category);
    List<Product> findByBrand(String brand);
    List<Product> findByCategoryAndBrand(String category, String brand);
    List<Product> findByActiveTrue();
    List<Product> findByNameContainingIgnoreCase(String name);

}
