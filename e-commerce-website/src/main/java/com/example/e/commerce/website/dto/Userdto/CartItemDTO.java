package com.example.e.commerce.website.dto.Userdto;

public class CartItemDTO {
    private Long productId;
    private String productName;
    private double price;
       private double originalPrice;
    private double discount;
    private double discountPercent;
    private int quantity;
    private byte[] mainImage;

  

    public CartItemDTO() {
    }
    
    public CartItemDTO(Long productId, String productName, double price, double originalPrice, double discount,
            double discountPercent, int quantity, byte[] mainImage) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.originalPrice = originalPrice;
        this.discount = discount;
        this.discountPercent = discountPercent;
        this.quantity = quantity;
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
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public byte[] getMainImage() {
        return mainImage;
    }
    public void setMainImage(byte[] mainImage) {
        this.mainImage = mainImage;
    }
   

}
