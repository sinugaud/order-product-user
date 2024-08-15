package com.javapedia.OrderEase.service;

import com.javapedia.OrderEase.dto.Orders;
import com.javapedia.OrderEase.entity.Order;
import com.javapedia.OrderEase.service.implement.ProductNotFoundException;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Order placeOrder(Order order) throws ProductNotFoundException;

    List<Order> getAllOrdersByUsername(String token);


    List<Orders> getAllOrders();

    Optional<Order> getOrderById(Long orderId);

    Order updateOrder(Long orderId, Order updatedOrder);

    Order updateOrderStatus(Long orderId, String newStatus);

    boolean cancelOrder(Long orderId);

    List<Order> getOrdersByUsername(String username);


}

