package com.example.e.commerce.website.Controllers.UserControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.e.commerce.website.Service.UserService.UserProductService;
import com.example.e.commerce.website.dto.Userdto.ProductUserDTO;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserProductService userService;

    @GetMapping("/showproducts")
    public ResponseEntity<?> getAllProducts() {
        try {
            List<ProductUserDTO> products = userService.getAllProducts();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/cart/{productId}")
    public ResponseEntity<?> addToCart(@PathVariable Long productId) {
        try {
            String user = getLoggedInUsername();
            if (user == null || user.isEmpty()) {
                return new ResponseEntity<>("Not logged in", HttpStatus.BAD_REQUEST);
            }
            boolean status = userService.addToCart(user, productId);
            if (status) {
                return new ResponseEntity<>("Product added to cart for user " + user, HttpStatus.OK);
            }
            return new ResponseEntity<>("Failed to add product to cart", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private String getLoggedInUsername() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null &&
                authentication.isAuthenticated() &&
                authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return null;
    }
}
