package com.example.e.commerce.website.dto.Admindto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.e.commerce.website.Model.Product;

public class ProductAdminDTO {

    private Long id;
    private String name;
    private String description;
    private double price;
    private Integer quantity;
    private String category;
    private String brand;
    private String sku;
    private List<byte[]> images;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
 // In ProductAdminDTO class, add this field
    private List<Long> imageIdsToRemove; // IDs of images to remove during update
    private List<Long> imageIds; 
    
    public ProductAdminDTO(Product saved) {
		// TODO Auto-generated constructor stub
	}

	public ProductAdminDTO() {
		// TODO Auto-generated constructor stub
	}

	public List<Long> getImageIds() {
		return imageIds;
	}

	public void setImageIds(List<Long> imageIds) {
		this.imageIds = imageIds;
	}

	// Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSku() {
        return sku;
    }

    public List<Long> getImageIdsToRemove() {
		return imageIdsToRemove;
	}

	public void setImageIdsToRemove(List<Long> imageIdsToRemove) {
		this.imageIdsToRemove = imageIdsToRemove;
	}

	public void setSku(String sku) {
        this.sku = sku;
    }

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

