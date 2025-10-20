package com.example.e.commerce.website.Service.AdminService;

import com.example.e.commerce.website.Model.Product;
import com.example.e.commerce.website.Model.ProductImage;
import com.example.e.commerce.website.Repo.ProductRepo;
import com.example.e.commerce.website.Repo.ProductImageRepo;
import com.example.e.commerce.website.dto.Admindto.ProductAdminDTO;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminProductService {

    @Autowired
    private ProductRepo productRepository;
    
    @Autowired
    private ProductImageRepo productImageRepo;

    public List<ProductAdminDTO> getAllProducts(String category, String brand) {
        List<Product> products;
        
        if (category != null && !category.isEmpty() && brand != null && !brand.isEmpty()) {
            products = productRepository.findByCategoryAndBrand(category, brand);
        } else if (category != null && !category.isEmpty()) {
            products = productRepository.findByCategory(category);
        } else if (brand != null && !brand.isEmpty()) {
            products = productRepository.findByBrand(brand);
        } else {
            products = productRepository.findAll();
        }

        return products.stream()
                .map(this::convertToAdminDTO)
                .collect(Collectors.toList());
    }

    public ProductAdminDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return null;
        }
        return convertToAdminDTO(product);
    }
    
    public List<ProductAdminDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToAdminDTO)
                .collect(Collectors.toList());
    }

    // Add Product with Images
    public ProductAdminDTO addProductWithImages(ProductAdminDTO dto, List<MultipartFile> images) throws IOException {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setCategory(dto.getCategory());
        product.setBrand(dto.getBrand());
        product.setSku(dto.getSku());
        product.setActive(dto.isActive());

        if (images != null && !images.isEmpty()) {
            List<ProductImage> imageEntities = new ArrayList<>();
            for (MultipartFile file : images) {
                if (file.isEmpty() || file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty()) {
                    continue;
                }
                ProductImage img = new ProductImage();
                img.setImageData(file.getBytes());
                img.setProduct(product);
                imageEntities.add(img);
            }
            if (!imageEntities.isEmpty()) {
                product.setImages(imageEntities);
            }
        }

        Product saved = productRepository.save(product);
        return convertToAdminDTO(saved);
    }

    // Update product with images
    public boolean updateProductWithImages(Long id, ProductAdminDTO dto, List<MultipartFile> images) {
        Optional<Product> opt = productRepository.findById(id);
        if (opt.isPresent()) {
            Product product = opt.get();
            
            if (dto.getName() != null) {
                product.setName(dto.getName());
            }
            if (dto.getDescription() != null) {
                product.setDescription(dto.getDescription());
            }
            if (dto.getPrice() != 0) {
                product.setPrice(dto.getPrice());
            }
            if (dto.getQuantity() != null) {
                product.setQuantity(dto.getQuantity());
            }
            if (dto.getCategory() != null) {
                product.setCategory(dto.getCategory());
            }
            if (dto.getBrand() != null) {
                product.setBrand(dto.getBrand());
            }
            if (dto.getSku() != null) {
                product.setSku(dto.getSku());
            }
            product.setActive(dto.isActive());

            // Remove images that are marked for deletion
            if (dto.getImageIdsToRemove() != null && !dto.getImageIdsToRemove().isEmpty()) {
                if (product.getImages() != null) {
                    List<ProductImage> imagesToRemove = product.getImages().stream()
                            .filter(img -> img != null && dto.getImageIdsToRemove().contains(img.getId()))
                            .collect(Collectors.toList());
                    
                    if (!imagesToRemove.isEmpty()) {
                        productImageRepo.deleteAll(imagesToRemove);
                        product.getImages().removeAll(imagesToRemove);
                    }
                }
            }
            
            // Add new images if provided
            if (images != null && !images.isEmpty()) {
                List<ProductImage> newImageEntities = new ArrayList<>();
                for (MultipartFile file : images) {
                    if (file.isEmpty() || file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty()) {
                        continue;
                    }
                    ProductImage img = new ProductImage();
                    try {
                        img.setImageData(file.getBytes());
                        img.setProduct(product);
                        newImageEntities.add(img);
                    } catch (IOException e) {
                        e.printStackTrace();
                        continue;
                    }
                }
                
                if (product.getImages() == null) {
                    product.setImages(new ArrayList<>());
                }
                product.getImages().addAll(newImageEntities);
            }

            productRepository.save(product);
            return true;
        }
        return false;
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private Product convertToEntity(ProductAdminDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setCategory(dto.getCategory());
        product.setBrand(dto.getBrand());
        product.setSku(dto.getSku());
        product.setActive(dto.isActive());

        if (dto.getImages() != null && !dto.getImages().isEmpty()) {
            List<ProductImage> imageList = dto.getImages()
                    .stream()
                    .map(bytes -> {
                        ProductImage img = new ProductImage();
                        img.setImageData(bytes);
                        img.setProduct(product);
                        return img;
                    })
                    .collect(Collectors.toList());
            product.setImages(imageList);
        }
        return product;
    }

    private ProductAdminDTO convertToAdminDTO(Product product) {
        ProductAdminDTO dto = new ProductAdminDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setCategory(product.getCategory());
        dto.setBrand(product.getBrand());
        dto.setSku(product.getSku());

        if (product.getImages() != null && !product.getImages().isEmpty()) {
            dto.setImages(product.getImages()
                    .stream()
                    .map(ProductImage::getImageData)
                    .collect(Collectors.toList()));
            
            dto.setImageIds(product.getImages()
                    .stream()
                    .map(ProductImage::getId)
                    .collect(Collectors.toList()));
        }

        dto.setActive(product.isActive());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        return dto;
    }
}