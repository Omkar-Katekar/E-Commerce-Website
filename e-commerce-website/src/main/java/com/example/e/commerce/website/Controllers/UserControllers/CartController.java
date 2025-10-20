package com.example.e.commerce.website.Controllers.UserControllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.e.commerce.website.Service.UserService.CartService;
import com.example.e.commerce.website.dto.Userdto.CartDTO;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

   @PostMapping("/add")
public ResponseEntity<?> addToCart(@RequestParam Long productId, @RequestParam int quantity) {
    try {
        String user = getloggedInUsername();
        System.out.println("User: " + user + " adding product: " + productId + " quantity: " + quantity);
        
        if (user == null || user.isEmpty()) {
            return new ResponseEntity<>("Not logged in", HttpStatus.BAD_REQUEST);
        }

        boolean result = cartService.addToCart(user, productId, quantity);
        if (result) {
            return new ResponseEntity<>("Product added to cart", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to add product to cart", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    @GetMapping("/view")
    public ResponseEntity<?> viewCart() {
        try {
            String user = getloggedInUsername();
            if (user == null || user.isEmpty()) {
                return new ResponseEntity<>("Not logged in", HttpStatus.BAD_REQUEST);
            }

            CartDTO cart = cartService.getCart(user);
            if (cart != null) {
                return new ResponseEntity<>(cart, HttpStatus.OK);
            }
            return new ResponseEntity<>("Cart is empty", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFromCart(@RequestParam Long productId) {
        try {
            String user = getloggedInUsername();
            if (user == null || user.isEmpty()) {
                return new ResponseEntity<>("Not logged in", HttpStatus.BAD_REQUEST);
            }

            boolean result = cartService.removeFromCart(user, productId);
            if (result) {
                return new ResponseEntity<>("Product removed from cart", HttpStatus.OK);
            }
            return new ResponseEntity<>("Failed to remove product from cart", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCartItem(@RequestParam Long productId, @RequestParam int quantity) {
        try {
            String user = getloggedInUsername();
            if (user == null || user.isEmpty()) {
                return new ResponseEntity<>("Not logged in", HttpStatus.BAD_REQUEST);
            }

            boolean result = cartService.updateCartItem(user, productId, quantity);
            if (result) {
                return new ResponseEntity<>("Cart updated", HttpStatus.OK);
            }
            return new ResponseEntity<>("Failed to update cart", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getloggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return null;
    }
}