package com.example.e.commerce.website.Controllers.UserControllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.e.commerce.website.Model.Order;

import com.example.e.commerce.website.Service.UserService.OrderService;
import com.example.e.commerce.website.dto.Userdto.OrderDTO;
import com.example.e.commerce.website.dto.Userdto.OrderRequestDTO;



@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDTO orderRequest) {
        try {
            String user = getloggedInUsername();
            if (user == null || user.isEmpty()) {
                return new ResponseEntity<>("Not logged in", HttpStatus.BAD_REQUEST);
            }

            Order order = orderService.createOrder(user, orderRequest);
            if (order != null) {
                return new ResponseEntity<>(order.getId() + " Order created successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Failed to create order", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/history")
    public ResponseEntity<?> getOrderHistory() {
        try {
            String user = getloggedInUsername();
            if (user == null || user.isEmpty()) {
                return new ResponseEntity<>("Not logged in", HttpStatus.BAD_REQUEST);
            }

            List<OrderDTO> orders = orderService.getOrderHistory(user);
            if (orders != null && !orders.isEmpty()) {
                return new ResponseEntity<>(orders, HttpStatus.OK);
            }
            return new ResponseEntity<>("No orders found", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/details/{orderId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable Long orderId) {
        try {
            String user = getloggedInUsername();
            if (user == null || user.isEmpty()) {
                return new ResponseEntity<>("Not logged in", HttpStatus.BAD_REQUEST);
            }

            OrderDTO order = orderService.getOrderDetails(user, orderId);
            if (order != null) {
                return new ResponseEntity<>(order, HttpStatus.OK);
            }
            return new ResponseEntity<>("Order not found", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
        try {
            String user = getloggedInUsername();
            if (user == null || user.isEmpty()) {
                return new ResponseEntity<>("Not logged in", HttpStatus.BAD_REQUEST);
            }

            boolean result = orderService.cancelOrder(user, orderId);
            if (result) {
                return new ResponseEntity<>("Order cancelled successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Failed to cancel order", HttpStatus.BAD_REQUEST);
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