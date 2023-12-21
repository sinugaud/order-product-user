package com.javapedia.OrderEase.service.implement;

import com.javapedia.OrderEase.Enum.OrderStatus;
import com.javapedia.OrderEase.dto.Product;
import com.javapedia.OrderEase.model.Order;
import com.javapedia.OrderEase.model.OrderItem;
import com.javapedia.OrderEase.repository.OrderRepository;
import com.javapedia.OrderEase.service.OrderService;
import com.javapedia.OrderEase.service.UserService;
import com.javapedia.OrderEase.service.productdelegate.ProductServiceDelegate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductServiceDelegate productServiceDelegate;
    @Autowired
    private UserService userService;

    @Override
    public List<Order> getOrdersByUsername(String username) {
//        System.out.println("Token in side method"+token);

//        String usernameFromToken = userService.getUsernameFromToken(token);


        return orderRepository.findByUsername(username);
    }


    @Override
    public Order createOrder(Order order) throws ProductNotFoundException {

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();

    }

    @Override
    public Optional<Order> getOrderById(Long orderId) {
        Optional<Order> existingOrder = Optional.of(orderRepository.findById(orderId).orElseThrow());

        Order order = existingOrder.get();
        return Optional.of(order);

    }

    @Override
    public Order updateOrder(Long orderId, Order updatedOrder) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order existingOrder = optionalOrder.get();

            // Update only if the fields are not null in the updatedOrder
            if (updatedOrder.getUsername() != null) {
                existingOrder.setUsername(updatedOrder.getUsername());
            }
            if (updatedOrder.getDate() != null) {
                existingOrder.setDate(updatedOrder.getDate());
            }
            if (updatedOrder.getStatus() != null) {
                existingOrder.setStatus(updatedOrder.getStatus());
            }
            if (updatedOrder.getTotalAmount() != 0) {
                existingOrder.setTotalAmount(updatedOrder.getTotalAmount());
            }
            if (updatedOrder.getOrderItems() != null) {
                existingOrder.setOrderItems(updatedOrder.getOrderItems());
            }

            return orderRepository.save(existingOrder);
        } else {
            // Handle scenario where the order with the given ID is not found
            // You might throw an exception or handle it based on your application's logic
            return null;
        }
    }

    @Override
    public Order updateOrderStatus(Long orderId, String newStatus) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order existingOrder = optionalOrder.get();

            // Assuming OrderStatus is an Enum and 'OrderStatus.valueOf(newStatus)' converts string to Enum
            existingOrder.setStatus(OrderStatus.valueOf(newStatus)); // Assuming Enum names match the input string

            return orderRepository.save(existingOrder);
        } else {
            return null; // Or you might throw an exception to handle the scenario where the order is not found
        }
    }

    @Override
    public boolean cancelOrder(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            // Check if the order can be canceled (based on status, business logic, etc.)
            if (order.getStatus() == OrderStatus.COMPLETED || order.getStatus() == OrderStatus.PROCESSING) {
                order.setStatus(OrderStatus.CANCELLED);
                orderRepository.save(order);
                return true;
            } else {
                // Order cannot be canceled (e.g., it's already completed)
                return false;
            }
        }

        return false; // Order not found
    }


}
