package com.example.e.commerce.website.dto.Userdto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.e.commerce.website.Model.OrderStatus;

public class AdminOrderDTO {
    private Long orderId;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private double totalAmount;
    private String shippingAddress;
    private String billingAddress;
    private String customerEmail;
    private String customerName;
    private String customerPhone;
    private List<OrderItemDTO> items;
    private PaymentInfoDTO paymentInfo;
    
    // Constructors
    public AdminOrderDTO() {}
    
    
    public AdminOrderDTO(Long orderId, LocalDateTime orderDate, OrderStatus status, double totalAmount,
            String shippingAddress, String billingAddress, String customerEmail, String customerName,
            String customerPhone, List<OrderItemDTO> items, PaymentInfoDTO paymentInfo) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.items = items;
        this.paymentInfo = paymentInfo;
    }


    // Getters and setters
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
    
    public String getShippingAddress() {
        return shippingAddress;
    }
    
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    
    public String getBillingAddress() {
        return billingAddress;
    }
    
    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }
    
    public String getCustomerEmail() {
        return customerEmail;
    }
    
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getCustomerPhone() {
        return customerPhone;
    }
    
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
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