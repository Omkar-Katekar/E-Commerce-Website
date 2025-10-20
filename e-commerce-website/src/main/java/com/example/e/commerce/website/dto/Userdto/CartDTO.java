package com.example.e.commerce.website.dto.Userdto;

import java.util.ArrayList;
import java.util.List;

public class CartDTO {
    private List<CartItemDTO> items;
    private double totalAmount;
    private double totalOriginalAmount;
    private double totalDiscount;
    
    // Constructors
    public CartDTO() {
        this.items = new ArrayList<>();
        this.totalAmount = 0.0;
    }
    
    
    
    public CartDTO(List<CartItemDTO> items, double totalAmount, double totalOriginalAmount, double totalDiscount) {
        this.items = items;
        this.totalAmount = totalAmount;
        this.totalOriginalAmount = totalOriginalAmount;
        this.totalDiscount = totalDiscount;
    }


public double getTotalOriginalAmount() { return totalOriginalAmount; }
    public void setTotalOriginalAmount(double totalOriginalAmount) { this.totalOriginalAmount = totalOriginalAmount; }
    
    public double getTotalDiscount() { return totalDiscount; }
    public void setTotalDiscount(double totalDiscount) { this.totalDiscount = totalDiscount; }
    // Getters and setters
    public List<CartItemDTO> getItems() {
        return items;
    }
    
    public void setItems(List<CartItemDTO> items) {
        this.items = items;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
