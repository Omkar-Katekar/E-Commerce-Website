package com.example.e.commerce.website.Service.AdminService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.e.commerce.website.Model.Order;
import com.example.e.commerce.website.Model.OrderItem;
import com.example.e.commerce.website.Model.OrderStatus;
import com.example.e.commerce.website.Model.signUp;
import com.example.e.commerce.website.Repo.OrderRepository;
import com.example.e.commerce.website.Repo.signUpRepo;
import com.example.e.commerce.website.dto.Userdto.AdminOrderDTO;
import com.example.e.commerce.website.dto.Userdto.OrderItemDTO;
import com.example.e.commerce.website.dto.Userdto.PaymentInfoDTO;

import jakarta.persistence.criteria.Join;

@Service
public class AdminOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private signUpRepo userRepository;

    public List<AdminOrderDTO> getAllOrdersWithFilters(String status, String startDate, 
                                                     String endDate, String customerEmail) {
        
        Specification<Order> spec = Specification.where(null);

        // Add status filter
        if (status != null && !status.isEmpty()) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(root.get("status"), OrderStatus.valueOf(status.toUpperCase())));
        }

        // Add date range filter
        if (startDate != null && endDate != null) {
            LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
            LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
            spec = spec.and((root, query, cb) -> 
                cb.between(root.get("orderDate"), start, end));
        }

        // Add customer email filter
        if (customerEmail != null && !customerEmail.isEmpty()) {
            spec = spec.and((root, query, cb) -> {
                Join<Order, signUp> userJoin = root.join("user");
                return cb.equal(userJoin.get("email"), customerEmail);
            });
        }

        // Get all orders without pagination
        List<Order> orders = orderRepository.findAll(spec);

        return orders.stream()
                .map(this::convertToAdminDTO)
                .collect(Collectors.toList());
    }

    public AdminOrderDTO getOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return null;
        }
        return convertToAdminDTO(order);
    }

    public boolean updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return false;
        }

        try {
            OrderStatus newStatus = OrderStatus.valueOf(status.toUpperCase());
            order.setStatus(newStatus);
            orderRepository.save(order);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public Map<String, Object> getOrderStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        // Total orders
        long totalOrders = orderRepository.count();
        stats.put("totalOrders", totalOrders);
        
        // Orders by status
        Map<String, Long> ordersByStatus = new HashMap<>();
        for (OrderStatus status : OrderStatus.values()) {
            long count = orderRepository.countByStatus(status);
            ordersByStatus.put(status.toString(), count);
        }
        stats.put("ordersByStatus", ordersByStatus);
        
        // Total revenue
        Double totalRevenue = orderRepository.sumTotalAmountByStatus(OrderStatus.DELIVERED);
        stats.put("totalRevenue", totalRevenue != null ? totalRevenue : 0.0);
        
        // Recent orders (last 7 days)
        LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
        long recentOrders = orderRepository.countByOrderDateAfter(weekAgo);
        stats.put("recentOrders", recentOrders);
        
        return stats;
    }

    private AdminOrderDTO convertToAdminDTO(Order order) {
        AdminOrderDTO dto = new AdminOrderDTO();
        dto.setOrderId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setBillingAddress(order.getBillingAddress());
        
        // Add customer info
        if (order.getUser() != null) {
            dto.setCustomerEmail(order.getUser().getEmail());
            dto.setCustomerName(order.getUser().getName());
            dto.setCustomerPhone(order.getUser().getPhone());
        }

        List<OrderItemDTO> itemDTOs = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            OrderItemDTO itemDTO = new OrderItemDTO();
            itemDTO.setProductId(item.getProduct().getId());
            itemDTO.setProductName(item.getProduct().getName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPrice(item.getPrice());
            
            if (!item.getProduct().getImages().isEmpty()) {
                itemDTO.setMainImage(item.getProduct().getImages().get(0).getImageData());
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