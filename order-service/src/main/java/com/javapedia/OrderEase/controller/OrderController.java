package com.javapedia.OrderEase.controller;

import com.javapedia.OrderEase.OrderNotFoundException;
import com.javapedia.OrderEase.dto.Product;
import com.javapedia.OrderEase.model.Order;
import com.javapedia.OrderEase.model.OrderItem;
import com.javapedia.OrderEase.repository.OrderRepository;
import com.javapedia.OrderEase.service.OrderService;
import com.javapedia.OrderEase.service.UserService;
import com.javapedia.OrderEase.service.implement.ProductNotFoundException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private ProductClient productClient;

    private final OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getAllproduct() {
        List<Product> products = productClient.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

//    @GetMapping("/{orderId}")
//    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId, @RequestHeader("Authorization") String token) {
//
//        if (userService.isUserLoggedIn(token)) {
//            Optional<Order> order = orderService.getOrderById(orderId);
//            return order.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
//                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//        }
//        return null;
//    }
//    @GetMapping("/{orderId}")
//    public ResponseEntity<?> getOrderById(@PathVariable Long orderId, @RequestHeader("Authorization") String token) {
//        if (userService.isUserLoggedIn(token)) {
//            Optional<Order> order = orderService.getOrderById(orderId);
//            if (order.isPresent()) {
//                return ResponseEntity.ok(order.get());
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please log in first");
//        }
//    }
@GetMapping("/{orderId}")
public ResponseEntity<?> getOrderById(@PathVariable Long orderId, @RequestHeader("Authorization") String token) {
    try {
        if (userService.isUserLoggedIn(token)) {
            Optional<Order> order = orderService.getOrderById(orderId);
            if (order.isPresent()) {
                return ResponseEntity.ok(order.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please log in first");
        }
    } catch (FeignException.Forbidden e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access: Token invalid or expired");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
    }
}



    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) throws ProductNotFoundException {
        Order createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @RequestBody Order updatedOrder) {
        Order updated = orderService.updateOrder(orderId, updatedOrder);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId, @RequestBody String newStatus) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, newStatus);
        if (updatedOrder != null) {
            return new ResponseEntity<>("Order status updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        boolean canceled = orderService.cancelOrder(orderId);
        if (canceled) {
            return new ResponseEntity<>("Order canceled successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Order not found or cannot be canceled", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/order-item/{orderId}/{productId}")
    public Order addItemToOrder(@PathVariable Long orderId, @PathVariable Long productId, Double quantity) throws ProductNotFoundException {
        Order order = orderService.getOrderById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        // Retrieve product details from Product Service using its API
        Product product = productClient.getProductById(productId);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProductId(productId); // Set the Product ID
        orderItem.setQuantity(2.0);
        orderItem.setPrice(product.getPrice()); // Set price or any other details from the product

        order.getOrderItems().add(orderItem);

        // Recalculate total amount for the order
        double totalAmount = order.getOrderItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }

    @GetMapping("/logged/orders")
    public ResponseEntity<List<Order>> getOrdersByToken(@RequestHeader("Authorization") String token) {
        try {
            // Log token for debugging
            System.out.println("Received Token: " + token);

            if (userService.isUserLoggedIn(token)) {
                String username = userService.getUsernameFromToken(token);
                System.out.println("Extracted Username: " + username);

                List<Order> orders = orderService.getOrdersByUsername(username);
                System.out.println("Orders retrieved: " + orders.size());

                return ResponseEntity.ok(orders);
            } else {
                System.out.println("User not logged in");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        } catch (FeignException.Forbidden e) {
            System.out.println("Forbidden Exception: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



}


