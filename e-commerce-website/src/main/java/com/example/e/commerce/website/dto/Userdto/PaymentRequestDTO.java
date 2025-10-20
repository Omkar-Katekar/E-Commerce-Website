package com.example.e.commerce.website.dto.Userdto;

import com.example.e.commerce.website.Model.PaymentMethod;

public class PaymentRequestDTO {
    private Long orderId;
    private PaymentMethod method;
    private CardDetailsDTO cardDetails; // If paying by card
    public PaymentRequestDTO() {
    }
    public PaymentRequestDTO(Long orderId, PaymentMethod method, CardDetailsDTO cardDetails) {
        this.orderId = orderId;
        this.method = method;
        this.cardDetails = cardDetails;
    }
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public PaymentMethod getMethod() {
        return method;
    }
    public void setMethod(PaymentMethod method) {
        this.method = method;
    }
    public CardDetailsDTO getCardDetails() {
        return cardDetails;
    }
    public void setCardDetails(CardDetailsDTO cardDetails) {
        this.cardDetails = cardDetails;
    }
    
    
}
