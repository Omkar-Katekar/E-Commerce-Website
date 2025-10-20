package com.example.e.commerce.website.Service.UserService;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.e.commerce.website.Model.Cart;
import com.example.e.commerce.website.Model.CartItem;
import com.example.e.commerce.website.Model.Product;
import com.example.e.commerce.website.Model.signUp;
import com.example.e.commerce.website.Repo.CartRepository;
import com.example.e.commerce.website.Repo.ProductRepo;
import com.example.e.commerce.website.Repo.signUpRepo;
import com.example.e.commerce.website.dto.Userdto.CartDTO;
import com.example.e.commerce.website.dto.Userdto.CartItemDTO;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepo productRepository;

    @Autowired
    private signUpRepo userRepository;

    // Helper method to calculate discounted price based on SKU
    private double calculateDiscountedPrice(double originalPrice, String sku) {
        try {
            // Try to parse SKU as discount percentage
            double discountPercent = Double.parseDouble(sku);
            
            // Validate discount percentage (must be between 1-100)
            if (discountPercent > 0 && discountPercent <= 100) {
                return originalPrice * (1 - discountPercent / 100);
            }
        } catch (NumberFormatException e) {
            // SKU is not a valid number, return original price
        }
        return originalPrice;
    }

    public boolean addToCart(String username, Long productId, int quantity) {
        signUp user = userRepository.findByEmail(username);
        if (user == null) {
            return false;
        }

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null || product.getQuantity() < quantity) {
            return false;
        }

        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setItems(new ArrayList<>());
            cartRepository.save(cart);
        }

        // Check if product already in cart
        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                cartRepository.save(cart);
                return true;
            }
        }

        // Add new item to cart
        CartItem newItem = new CartItem();
        newItem.setCart(cart);
        newItem.setProduct(product);
        newItem.setQuantity(quantity);
        cart.getItems().add(newItem);
        cartRepository.save(cart);

        return true;
    }

    public CartDTO getCart(String username) {
        signUp user = userRepository.findByEmail(username);
        if (user == null) {
            return null;
        }

        Cart cart = cartRepository.findByUser(user);
        if (cart == null || cart.getItems().isEmpty()) {
            return null;
        }

        CartDTO cartDTO = new CartDTO();
        cartDTO.setTotalAmount(0.0);
        cartDTO.setTotalOriginalAmount(0.0); // Add original total amount
        cartDTO.setTotalDiscount(0.0); // Add total discount
        List<CartItemDTO> items = new ArrayList<>();

        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            double originalPrice = product.getPrice();
            double discountedPrice = calculateDiscountedPrice(originalPrice, product.getSku());
            double itemDiscount = originalPrice - discountedPrice;
            
            CartItemDTO itemDTO = new CartItemDTO();
            itemDTO.setProductId(product.getId());
            itemDTO.setProductName(product.getName());
            itemDTO.setOriginalPrice(originalPrice); // Set original price
            itemDTO.setPrice(discountedPrice); // Set discounted price
            itemDTO.setDiscount(itemDiscount); // Set discount amount
            itemDTO.setQuantity(item.getQuantity());
            
            // Calculate discount percentage
            double discountPercent = 0;
            if (originalPrice > 0) {
                discountPercent = (itemDiscount / originalPrice) * 100;
            }
            itemDTO.setDiscountPercent(discountPercent);
            
            // Get first image if available
            if (!product.getImages().isEmpty()) {
                itemDTO.setMainImage(product.getImages().get(0).getImageData());
            }
            
            items.add(itemDTO);
            
            // Update totals
            cartDTO.setTotalOriginalAmount(cartDTO.getTotalOriginalAmount() + (originalPrice * item.getQuantity()));
            cartDTO.setTotalAmount(cartDTO.getTotalAmount() + (discountedPrice * item.getQuantity()));
            cartDTO.setTotalDiscount(cartDTO.getTotalDiscount() + (itemDiscount * item.getQuantity()));
        }

        cartDTO.setItems(items);
        return cartDTO;
    }

    public boolean removeFromCart(String username, Long productId) {
        signUp user = userRepository.findByEmail(username);
        if (user == null) {
            return false;
        }

        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            return false;
        }

        boolean removed = cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        if (removed) {
            cartRepository.save(cart);
        }
        
        return removed;
    }

    public boolean updateCartItem(String username, Long productId, int quantity) {
        if (quantity <= 0) {
            return removeFromCart(username, productId);
        }

        signUp user = userRepository.findByEmail(username);
        if (user == null) {
            return false;
        }

        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            return false;
        }

        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(productId)) {
                Product product = productRepository.findById(productId).orElse(null);
                if (product != null && product.getQuantity() >= quantity) {
                    item.setQuantity(quantity);
                    cartRepository.save(cart);
                    return true;
                }
            }
        }
        
        return false;
    }
}