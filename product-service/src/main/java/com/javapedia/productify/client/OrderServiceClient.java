package com.javapedia.productify.client;

import com.javapedia.productify.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ORDER-SERVICE")
public interface OrderServiceClient {


    @GetMapping
    public List<OrderDTO> getAllOrdersByUsername(@RequestHeader("Authorization") String token);


    @GetMapping("/api/orders/{orderId}")
    public OrderDTO getOrderById(@PathVariable Long orderId );


    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderDTO order, @RequestHeader("Authorization") String token) ;


    @PutMapping("/api/orders/{orderId}")
    public OrderDTO updateOrder(@PathVariable Long orderId, @RequestBody OrderDTO updatedOrder, @RequestHeader("Authorization") String token);


    @PutMapping("/{orderId}/status")
    public String updateOrderStatus(@PathVariable Long orderId, @RequestBody String newStatus, @RequestHeader("Authorization") String token);


    @DeleteMapping("/{orderId}")
    public String cancelOrder(@PathVariable Long orderId);


    @GetMapping("/order-item/{orderId}/{productId}")
    public OrderDTO addItemToOrder(@PathVariable Long orderId, @PathVariable Long productId, Double quantity);


    @GetMapping("/logged/orders")
    public List<OrderDTO> getOrdersByLoggedInUser(@RequestHeader("Authorization") String token);


}


