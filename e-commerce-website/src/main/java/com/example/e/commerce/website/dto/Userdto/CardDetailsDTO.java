package com.example.e.commerce.website.dto.Userdto;

public class CardDetailsDTO {
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String cardholderName;
    // Getters and setters
    public CardDetailsDTO() {
    }
    public CardDetailsDTO(String cardNumber, String expiryDate, String cvv, String cardholderName) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.cardholderName = cardholderName;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public String getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    public String getCvv() {
        return cvv;
    }
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    public String getCardholderName() {
        return cardholderName;
    }
    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    
}
