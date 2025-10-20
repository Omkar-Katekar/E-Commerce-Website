package com.example.e.commerce.website.Controllers.UserControllers;

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



import com.example.e.commerce.website.Service.UserService.PaymentService;

import com.example.e.commerce.website.dto.Userdto.PaymentInfoDTO;
import com.example.e.commerce.website.dto.Userdto.PaymentRequestDTO;
import com.example.e.commerce.website.dto.Userdto.PaymentResultDTO;


@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequestDTO paymentRequest) {
        try {
            String user = getloggedInUsername();
            if (user == null || user.isEmpty()) {
                return new ResponseEntity<>("Not logged in", HttpStatus.BAD_REQUEST);
            }

            PaymentResultDTO result = paymentService.processPayment(user, paymentRequest);
            if (result.isSuccess()) {
                return new ResponseEntity<>(result.getMessage(), HttpStatus.OK);
            }
            return new ResponseEntity<>(result.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/details/{orderId}")
    public ResponseEntity<?> getPaymentDetails(@PathVariable Long orderId) {
        try {
            String user = getloggedInUsername();
            if (user == null || user.isEmpty()) {
                return new ResponseEntity<>("Not logged in", HttpStatus.BAD_REQUEST);
            }

            PaymentInfoDTO paymentInfo = paymentService.getPaymentDetails(user, orderId);
            if (paymentInfo != null) {
                return new ResponseEntity<>(paymentInfo, HttpStatus.OK);
            }
            return new ResponseEntity<>("Payment details not found", HttpStatus.BAD_REQUEST);
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