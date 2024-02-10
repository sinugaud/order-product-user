package com.javapedia.OrderEase.service.implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javapedia.OrderEase.Enum.OrderStatus;
import com.javapedia.OrderEase.dto.OrderPlacedEventDTO;
import com.javapedia.OrderEase.model.Order;
import com.javapedia.OrderEase.model.OrderItem;
import com.javapedia.OrderEase.repository.OrderItemRepository;
import com.javapedia.OrderEase.repository.OrderRepository;
import com.javapedia.OrderEase.service.OrderService;
import com.javapedia.OrderEase.service.UserService;
import com.javapedia.OrderEase.service.productdelegate.ProductServiceDelegate;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    ObjectMapper objectMapper;


    @Override
    public List<Order> getOrdersByUsername(String username) {
//        System.out.println("Token in side method"+token);

//        String usernameFromToken = userService.getUsernameFromToken(token);


        return orderRepository.findByUsername(username);
    }

    @Override
    public List<Order> getAllOrdersByUsername(String token) {
        String username = userService.getUsernameFromToken(token);
        return orderRepository.findAllByUsername(username);

    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        // Retrieve the order by ID
        Optional<Order> optionalOrder = orderRepository.findById(id);
        log.info("Order by id {} {}", id, optionalOrder);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
            log.info("OrderItem by order {} ", orderItems);
            order.setOrderItems(orderItems);
            log.info("Order after fetching OrderItem{} ", order);

        }
        return optionalOrder;
    }


    @Override
    public Order placeOrder(Order order) throws ProductNotFoundException {
        Order savedOrder = orderRepository.save(order);
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItem.setOrder(savedOrder);
            orderItemRepository.save(orderItem);
        }


        // Create OrderPlacedEventDTO with the saved order ID
        OrderPlacedEventDTO orderPlacedEventDTO = new OrderPlacedEventDTO(savedOrder.getId());

        // Create message properties and set content type
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");

        try {
            // Serialize OrderPlacedEventDTO to byte array
            byte[] messageBody = objectMapper.writeValueAsBytes(orderPlacedEventDTO);

            // Create a RabbitMQ message
            Message message = new Message(messageBody, messageProperties);

            // Send the message to the specified exchange and routing key
            rabbitTemplate.convertAndSend("order-exchange", "order.placed", message);

            log.info("Order placed event sent to RabbitMQ: {}", orderPlacedEventDTO);
        } catch (JsonProcessingException e) {
            log.error("Error serializing OrderPlacedEventDTO", e);
            // Handle the exception as needed
        }

        return savedOrder;
    }


    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();

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

            existingOrder.setStatus(OrderStatus.valueOf(newStatus)); // Assuming Enum names match the input string

            return orderRepository.save(existingOrder);
        } else {
            return null;
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

        return false;
    }


}
