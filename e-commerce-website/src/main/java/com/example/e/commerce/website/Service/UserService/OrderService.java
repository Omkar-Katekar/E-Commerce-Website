package com.example.e.commerce.website.Service.UserService;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.e.commerce.website.Model.Cart;
import com.example.e.commerce.website.Model.CartItem;
import com.example.e.commerce.website.Model.Order;
import com.example.e.commerce.website.Model.OrderItem;
import com.example.e.commerce.website.Model.OrderStatus;
import com.example.e.commerce.website.Model.Product;
import com.example.e.commerce.website.Model.signUp;
import com.example.e.commerce.website.Repo.CartRepository;
import com.example.e.commerce.website.Repo.OrderRepository;
import com.example.e.commerce.website.Repo.ProductRepo;
import com.example.e.commerce.website.Repo.signUpRepo;
import com.example.e.commerce.website.dto.Userdto.OrderDTO;
import com.example.e.commerce.website.dto.Userdto.OrderItemDTO;
import com.example.e.commerce.website.dto.Userdto.OrderRequestDTO;
import com.example.e.commerce.website.dto.Userdto.PaymentInfoDTO;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private signUpRepo userRepository;

    @Autowired
    private ProductRepo productRepository;

    // Helper method to calculate discounted price based on SKU
    private double calculateDiscountedPrice(double originalPrice, String sku) {
        try {
            double discountPercent = Double.parseDouble(sku);
            if (discountPercent > 0 && discountPercent <= 100) {
                return originalPrice * (1 - discountPercent / 100);
            }
        } catch (NumberFormatException e) {
            // SKU is not a valid number
        }
        return originalPrice;
    }

    public Order createOrder(String username, OrderRequestDTO orderRequest) {
        signUp user = userRepository.findByEmail(username);
        if (user == null) {
            return null;
        }

        Cart cart = cartRepository.findByUser(user);
        if (cart == null || cart.getItems().isEmpty()) {
            return null;
        }

        // Calculate total amount with discounts
        double totalAmount = 0.0;
        double totalOriginalAmount = 0.0;
        
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            double originalPrice = product.getPrice();
            double discountedPrice = calculateDiscountedPrice(originalPrice, product.getSku());
            
            totalOriginalAmount += originalPrice * item.getQuantity();
            totalAmount += discountedPrice * item.getQuantity();
        }

        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(totalAmount);
        order.setShippingAddress(orderRequest.getShippingAddress());
        order.setBillingAddress(orderRequest.getBillingAddress());
        order.setStatus(OrderStatus.PENDING);
        order.setItems(new ArrayList<>());

        // Add order items
        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();
            double originalPrice = product.getPrice();
            double discountedPrice = calculateDiscountedPrice(originalPrice, product.getSku());
            
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(discountedPrice); // Store discounted price
            orderItem.setOriginalPrice(originalPrice); // Store original price
            order.getItems().add(orderItem);

            // Update product stock
            product.setQuantity(product.getQuantity() - cartItem.getQuantity());
            productRepository.save(product);
        }

        // Save order and clear cart
        orderRepository.save(order);
        cart.getItems().clear();
        cartRepository.save(cart);

        return order;
    }

    public List<OrderDTO> getOrderHistory(String username) {
        signUp user = userRepository.findByEmail(username);
        if (user == null) {
            return null;
        }

        List<Order> orders = orderRepository.findByUserOrderByOrderDateDesc(user);
        List<OrderDTO> orderDTOs = new ArrayList<>();

        for (Order order : orders) {
            orderDTOs.add(convertToDTO(order));
        }

        return orderDTOs;
    }

    public OrderDTO getOrderDetails(String username, Long orderId) {
        signUp user = userRepository.findByEmail(username);
        if (user == null) {
            return null;
        }

        Order order = orderRepository.findByIdAndUser(orderId, user);
        if (order == null) {
            return null;
        }

        return convertToDTO(order);
    }

    public boolean cancelOrder(String username, Long orderId) {
        signUp user = userRepository.findByEmail(username);
        if (user == null) {
            return false;
        }

        Order order = orderRepository.findByIdAndUser(orderId, user);
        if (order == null || order.getStatus() != OrderStatus.PENDING) {
            return false;
        }

        // Restore product quantities
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() + item.getQuantity());
            productRepository.save(product);
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        return true;
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());

        List<OrderItemDTO> itemDTOs = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            double originalPrice = item.getOriginalPrice();
            double discountedPrice = item.getPrice();
            double discount = originalPrice - discountedPrice;
            double discountPercent = (discount / originalPrice) * 100;
            
            OrderItemDTO itemDTO = new OrderItemDTO();
            itemDTO.setProductId(product.getId());
            itemDTO.setProductName(product.getName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPrice(discountedPrice);
            itemDTO.setOriginalPrice(originalPrice);
            itemDTO.setDiscount(discount);
            itemDTO.setDiscountPercent(discountPercent);
            
            if (!product.getImages().isEmpty()) {
                itemDTO.setMainImage(product.getImages().get(0).getImageData());
            }
            
            itemDTOs.add(itemDTO);
        }
        dto.setItems(itemDTOs);

        if (order.getPayment() != null) {
            PaymentInfoDTO paymentInfo = new PaymentInfoDTO();
            paymentInfo.setMethod(order.getPayment().getMethod());
            paymentInfo.setStatus(order.getPayment().getStatus());
            paymentInfo.setTransactionId(order.getPayment().getTransactionId());
            paymentInfo.setPaymentDate(order.getPayment().getPaymentDate());
            dto.setPaymentInfo(paymentInfo);
        }

        return dto;
    }
}