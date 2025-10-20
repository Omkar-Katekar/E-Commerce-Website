package com.example.e.commerce.website.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.e.commerce.website.Model.Order;
import com.example.e.commerce.website.Model.OrderStatus;
import com.example.e.commerce.website.Model.Payment;
import com.example.e.commerce.website.Model.PaymentMethod;
import com.example.e.commerce.website.Model.PaymentStatus;

import com.example.e.commerce.website.Model.signUp;
import com.example.e.commerce.website.Repo.OrderRepository;
import com.example.e.commerce.website.Repo.PaymentRepository;

import com.example.e.commerce.website.Repo.signUpRepo;
import com.example.e.commerce.website.dto.Userdto.PaymentInfoDTO;
import com.example.e.commerce.website.dto.Userdto.PaymentRequestDTO;
import com.example.e.commerce.website.dto.Userdto.PaymentResultDTO;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;


@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private signUpRepo userRepository;

    @Transactional
    public PaymentResultDTO processPayment(String username, PaymentRequestDTO paymentRequest) {
        try {
            signUp user = userRepository.findByEmail(username);
            if (user == null) {
                return new PaymentResultDTO(false, "User not found");
            }

            // Use findById instead of findByIdAndUser to get the order without user constraint
            Order order = orderRepository.findById(paymentRequest.getOrderId()).orElse(null);
            if (order == null) {
                return new PaymentResultDTO(false, "Order not found");
            }

            // Check if the order belongs to the authenticated user
            if (!order.getUser().getEmail().equals(username)) {
                return new PaymentResultDTO(false, "Order does not belong to user");
            }

            // Check if payment already exists for this order
            Payment existingPayment = paymentRepository.findByOrder(order);
            if (existingPayment != null) {
                // Update existing payment instead of creating new one
                return updateExistingPayment(existingPayment, paymentRequest, order);
            }

            if (order.getStatus() != OrderStatus.PENDING) {
                return new PaymentResultDTO(false, "Order cannot be paid for");
            }

            // Simulate payment processing
            boolean paymentSuccess = simulatePaymentProcessing(paymentRequest);
            
            if (paymentSuccess) {
                // Create payment record
                Payment payment = new Payment();
                payment.setOrder(order);
                payment.setAmount(order.getTotalAmount());
                payment.setMethod(paymentRequest.getMethod());
                payment.setStatus(PaymentStatus.COMPLETED);
                payment.setTransactionId(generateTransactionId());
                payment.setPaymentDate(LocalDateTime.now());
                
                paymentRepository.save(payment);

                // Update order status
                order.setStatus(OrderStatus.CONFIRMED);
                order.setPayment(payment);
                orderRepository.save(order);

                return new PaymentResultDTO(true, "Payment processed successfully");
            } else {
                // Create failed payment record
                Payment payment = new Payment();
                payment.setOrder(order);
                payment.setAmount(order.getTotalAmount());
                payment.setMethod(paymentRequest.getMethod());
                payment.setStatus(PaymentStatus.FAILED);
                payment.setPaymentDate(LocalDateTime.now());
                
                paymentRepository.save(payment);

                return new PaymentResultDTO(false, "Payment failed");
            }
        } catch (DataIntegrityViolationException e) {
            return new PaymentResultDTO(false, "Payment already exists for this order");
        } catch (Exception e) {
            return new PaymentResultDTO(false, "Payment processing error: " + e.getMessage());
        }
    }

    private PaymentResultDTO updateExistingPayment(Payment existingPayment, PaymentRequestDTO paymentRequest, Order order) {
        // For failed payments, allow retry
        if (existingPayment.getStatus() == PaymentStatus.FAILED) {
            boolean paymentSuccess = simulatePaymentProcessing(paymentRequest);
            
            if (paymentSuccess) {
                existingPayment.setStatus(PaymentStatus.COMPLETED);
                existingPayment.setTransactionId(generateTransactionId());
                existingPayment.setPaymentDate(LocalDateTime.now());
                existingPayment.setMethod(paymentRequest.getMethod());
                
                paymentRepository.save(existingPayment);

                // Update order status
                order.setStatus(OrderStatus.CONFIRMED);
                orderRepository.save(order);

                return new PaymentResultDTO(true, "Payment processed successfully (retry)");
            } else {
                return new PaymentResultDTO(false, "Payment failed again");
            }
        } else {
            return new PaymentResultDTO(false, "Payment already processed for this order");
        }
    }

    public PaymentInfoDTO getPaymentDetails(String username, Long orderId) {
        signUp user = userRepository.findByEmail(username);
        if (user == null) {
            return null;
        }

        Order order = orderRepository.findByIdAndUser(orderId, user);
        if (order == null || order.getPayment() == null) {
            return null;
        }

        Payment payment = order.getPayment();
        PaymentInfoDTO paymentInfo = new PaymentInfoDTO();
        paymentInfo.setMethod(payment.getMethod());
        paymentInfo.setStatus(payment.getStatus());
        paymentInfo.setTransactionId(payment.getTransactionId());
        paymentInfo.setPaymentDate(payment.getPaymentDate());

        return paymentInfo;
    }

    private boolean simulatePaymentProcessing(PaymentRequestDTO paymentRequest) {
        // Simulate payment processing - in real application, integrate with payment gateway
        // For demo purposes, assume all payments succeed except if card number starts with "41"
        if (paymentRequest.getMethod() == PaymentMethod.CREDIT_CARD || 
            paymentRequest.getMethod() == PaymentMethod.DEBIT_CARD) {
            if (paymentRequest.getCardDetails() != null && 
                paymentRequest.getCardDetails().getCardNumber().startsWith("41")) {
                return false; // Simulate declined card
            }
        }
        return true;
    }

    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }
}
