package com.restaurant.order.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.order.entity.MenuItem;
import com.restaurant.order.entity.Order;
import com.restaurant.order.entity.OrderItem;
import com.restaurant.order.repository.MenuItemRepository;
import com.restaurant.order.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Transactional
    public Order createOrder(Order order) {
        // Validate order items
        for (OrderItem item : order.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(item.getMenuItem().getId())
                    .orElseThrow(() -> new RuntimeException("Menu item not found"));

            if (item.getQuantity() > menuItem.getAvailableQuantity()) {
                throw new RuntimeException("Insufficient quantity for menu item: " + menuItem.getName());
            }

            // Update inventory
            menuItem.setAvailableQuantity(menuItem.getAvailableQuantity() - item.getQuantity());
            menuItemRepository.save(menuItem);

            // Calculate subtotal
            item.setSubtotal(menuItem.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        // Calculate total amount
        BigDecimal totalAmount = order.getItems().stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(totalAmount);

        // Set order status and creation time
        order.setStatus(Order.Status.RECEIVED);
        order.setCreatedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}