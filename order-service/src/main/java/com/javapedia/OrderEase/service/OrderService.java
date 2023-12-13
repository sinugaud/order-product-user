package com.javapedia.OrderEase.service;

import com.javapedia.OrderEase.model.Order;
import com.javapedia.OrderEase.service.implement.ProductNotFoundException;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(Order order) throws ProductNotFoundException;

    List<Order> getAllOrders();

    Optional<Order> getOrderById(Long orderId);

    Order updateOrder(Long orderId, Order updatedOrder);

    Order updateOrderStatus(Long orderId, String newStatus);

    boolean cancelOrder(Long orderId);

}

