package com.example.e.commerce.website.dto.Userdto;


public class OrderItemDTO {
    private Long productId;
    private String productName;
    private int quantity;
    private double price;
     private double originalPrice;
    private double discount;
    private double discountPercent;
    private byte[] mainImage;
    public OrderItemDTO() {
    }
   
    public OrderItemDTO(Long productId, String productName, int quantity, double price, double originalPrice,
            double discount, double discountPercent, byte[] mainImage) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.originalPrice = originalPrice;
        this.discount = discount;
        this.discountPercent = discountPercent;
        this.mainImage = mainImage;
    }

    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public byte[] getMainImage() {
        return mainImage;
    }
    public void setMainImage(byte[] mainImage) {
        this.mainImage = mainImage;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }
    
}