package com.javapedia.OrderEase.controller;

import com.javapedia.OrderEase.model.OrderItem;
import com.javapedia.OrderEase.service.OrderItemService;
import com.javapedia.OrderEase.service.implement.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-items")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public ResponseEntity<List<OrderItem>> listOrderItems() throws OrderItemNotFoundException {
        return new ResponseEntity<>(orderItemService.getAllOrderItem(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderItem> CreateOrderItem(@RequestBody OrderItem orderItem) throws ProductNotFoundException {
        return new ResponseEntity<>(orderItemService.createOrderItem(orderItem), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<OrderItem> updateOrderItem(@RequestBody OrderItem orderItem) throws OrderItemNotFoundException {
        return new ResponseEntity<>(orderItemService.updateOrderItem(orderItem), HttpStatus.CREATED);
    }


}

