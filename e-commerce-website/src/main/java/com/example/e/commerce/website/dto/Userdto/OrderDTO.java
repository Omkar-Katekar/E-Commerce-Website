package com.example.e.commerce.website.dto.Userdto;

import java.time.LocalDateTime;

import com.example.e.commerce.website.Model.OrderStatus;
import java.util.*;

public class OrderDTO {
    private Long orderId;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private double totalAmount;
    private List<OrderItemDTO> items;

    public OrderDTO(Long orderId, LocalDateTime orderDate, OrderStatus status, double totalAmount,
            List<OrderItemDTO> items, PaymentInfoDTO paymentInfo) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.items = items;
        this.paymentInfo = paymentInfo;
    }
    private PaymentInfoDTO paymentInfo;
    public OrderDTO() {
    }
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public List<OrderItemDTO> getItems() {
        return items;
    }
    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }
    public PaymentInfoDTO getPaymentInfo() {
        return paymentInfo;
    }
    public void setPaymentInfo(PaymentInfoDTO paymentInfo) {
        this.paymentInfo = paymentInfo;
    }
   
    
}