package com.example.e.commerce.website.dto.Userdto;

import java.time.LocalDateTime;

import com.example.e.commerce.website.Model.PaymentMethod;
import com.example.e.commerce.website.Model.PaymentStatus;

public class PaymentInfoDTO {
    private PaymentMethod method;
    private PaymentStatus status;
    private String transactionId;
    private LocalDateTime paymentDate;
    public PaymentInfoDTO() {
    }
    public PaymentInfoDTO(PaymentMethod method, PaymentStatus status, String transactionId, LocalDateTime paymentDate) {
        this.method = method;
        this.status = status;
        this.transactionId = transactionId;
        this.paymentDate = paymentDate;
    }
    public PaymentMethod getMethod() {
        return method;
    }
    public void setMethod(PaymentMethod method) {
        this.method = method;
    }
    public PaymentStatus getStatus() {
        return status;
    }
    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
    public String getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }
    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
    
}
