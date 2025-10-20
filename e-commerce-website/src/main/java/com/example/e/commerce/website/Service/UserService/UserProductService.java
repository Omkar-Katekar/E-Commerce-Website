package com.example.e.commerce.website.Service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.e.commerce.website.Model.Product;
import com.example.e.commerce.website.Repo.ProductRepo;
import com.example.e.commerce.website.dto.Userdto.ProductUserDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProductService {

    @Autowired
    private ProductRepo productRepository;

    public List<ProductUserDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    public boolean addToCart(String username, Long productId) {
        return productRepository.findById(productId).isPresent();
    }

    private ProductUserDTO convertToUserDTO(Product product) {
        ProductUserDTO dto = new ProductUserDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCategory(product.getCategory());
        dto.setBrand(product.getBrand());
        dto.setSku(product.getSku());
        List<byte[]> imageList = product.getImages()
                .stream()
                .map(img -> img.getImageData())  
                .collect(Collectors.toList());
        dto.setImages(imageList);
        return dto;
    }
}
