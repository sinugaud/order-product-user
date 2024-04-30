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
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@Log4j2
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

//    @GetMapping
//    public ResponseEntity<List<Order>> getAllOrders(String token) {
//
//        List<Order> orders = orderService.getAllOrders();
//        return new ResponseEntity<>(orders, HttpStatus.OK);
//    }

    @GetMapping("all")
    public ResponseEntity<List<Order>> getAllOrder() {
        HttpHeaders httpHeaders = new HttpHeaders();
        List<String> authorization = httpHeaders.get("Authorization");
        log.info("httpHeaders {},",httpHeaders);
        log.info("auth header : {},", authorization);

        List<Order> orders = orderService.getAllOrders();
        log.info("Orders retrieved: {}", orders.size());

        return ResponseEntity.ok(orders);
    }


@GetMapping
public ResponseEntity<List<Order>> getAllOrdersByUsername(@RequestHeader("Authorization") String token) {
    try {
        // Log token for debugging
        log.info("Received Token: {}", token);

        if (userService.isUserLoggedIn(token)) {
//                String username = userService.getUsernameFromToken(token);

            List<Order> orders = orderService.getAllOrdersByUsername(token);
            log.info("Orders retrieved: {}", orders.size());

            return ResponseEntity.ok(orders);
        } else {
            log.info("User not logged in");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    } catch (FeignException.Forbidden e) {
        log.info("Forbidden Exception: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    } catch (Exception e) {
        log.info("Error occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

//    @GetMapping("/{orderId}")
//    public ResponseEntity<?> getOrderById(@PathVariable Long orderId, @RequestHeader("Authorization") String token) {
//        try {
//            if (userService.isUserLoggedIn(token)) {
//                Optional<Order> order = orderService.getOrderById(orderId);
//                if (order.isPresent()) {
//                    return ResponseEntity.ok(order.get());
//                } else {
//                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
//                }
//            } else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please log in first");
//            }
//        } catch (FeignException.Forbidden e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access: Token invalid or expired");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
//        }
//    }

@GetMapping("/{orderId}")
public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
//        try {
//            if (userService.isUserLoggedIn(token)) {
    Optional<Order> order = orderService.getOrderById(orderId);
    if (order.isPresent()) {
        return ResponseEntity.ok(order.get());
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
    }
//            }
//        else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please log in first");
//            }
//        } catch (FeignException.Forbidden e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access: Token invalid or expired");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
}
//    }


@PostMapping
public ResponseEntity<Order> createOrder(@RequestBody Order order, @RequestHeader("Authorization") String token) throws ProductNotFoundException {

    try {
        log.info("Received Token: {}", token);

        if (userService.isUserLoggedIn(token)) {
            Order createdOrder = orderService.placeOrder(order);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } else {
            log.info("User not logged in");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    } catch (FeignException.Forbidden e) {
        log.info("Forbidden Exception: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    } catch (Exception e) {
        log.info("Error occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

//        Order createdOrder = orderService.createOrder(order);
//        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
}

@PutMapping("/{orderId}")
public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @RequestBody Order updatedOrder, @RequestHeader("Authorization") String token) {
    try {
        log.info("Received Token: {}", token);
        if (userService.isUserLoggedIn(token)) {
            Order updated = orderService.updateOrder(orderId, updatedOrder);
            if (updated != null) {
                return new ResponseEntity<>(updated, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } else {
            log.info("User not logged in");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    } catch (FeignException.Forbidden e) {
        log.info("Forbidden Exception: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    } catch (Exception e) {
        log.info("Error occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


}

@PutMapping("/{orderId}/status")
public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId, @RequestBody String newStatus, @RequestHeader("Authorization") String token) {
    try {
        if (userService.isUserLoggedIn(token)) {
            Order updatedOrder = orderService.updateOrderStatus(orderId, newStatus);
            if (updatedOrder != null) {
                return new ResponseEntity<>("Order status updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please log in first");
        }
    } catch (FeignException.Forbidden e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access: Token invalid or expired");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
    }


//        Order updatedOrder = orderService.updateOrderStatus(orderId, newStatus);
//        if (updatedOrder != null) {
//            return new ResponseEntity<>("Order status updated successfully", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
//        }
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

//    @GetMapping("/order-item/{orderId}/{productId}")
//    public Order addItemToOrder(@PathVariable Long orderId, @PathVariable Long productId, Double quantity) throws ProductNotFoundException {
//        Order order = orderService.getOrderById(orderId)
//                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
//
//        // Retrieve product details from Product Service using its API
//        Product product = productClient.getProductById(productId);
//
//        OrderItem orderItem = new OrderItem();
//        orderItem.setOrder(order);
//        orderItem.setProductId(productId); // Set the Product ID
//        orderItem.setQuantity(2.0);
//        orderItem.setPrice(product.getPrice()); // Set price or any other details from the product
//
//        order.getOrderItems().add(orderItem);
//
//        // Recalculate total amount for the order
//        double totalAmount = order.getOrderItems().stream()
//                .mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
//        order.setTotalAmount(totalAmount);
//
//        return orderRepository.save(order);
//    }

@GetMapping("/logged/orders")
public ResponseEntity<List<Order>> getOrdersByLoggedInUser(@RequestHeader("Authorization") String token) {
    try {

        if (userService.isUserLoggedIn(token)) {
            String username = userService.getUsernameFromToken(token);
            log.info("Extracted Username: {}", username);

            List<Order> orders = orderService.getOrdersByUsername(username);
            log.info("Orders retrieved: {}", orders.size());

            return ResponseEntity.ok(orders);
        } else {
            System.out.println("User not logged in");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    } catch (FeignException.Forbidden e) {
        log.info("Forbidden Exception:{} ", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    } catch (Exception e) {
        log.info("Error occurred: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}


}


