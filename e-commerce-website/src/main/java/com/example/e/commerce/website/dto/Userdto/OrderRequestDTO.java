package com.example.e.commerce.website.dto.Userdto;

import com.example.e.commerce.website.Model.PaymentMethod;

public class OrderRequestDTO {
    private String shippingAddress;
    private String billingAddress;
    private PaymentMethod paymentMethod;
    public OrderRequestDTO() {
    }
    public OrderRequestDTO(String shippingAddress, String billingAddress, PaymentMethod paymentMethod) {
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.paymentMethod = paymentMethod;
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
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    
}
