package com.javapedia.productify.client;

import com.javapedia.productify.dto.OrderItemDto;
import com.javapedia.productify.exeptions.OrderItemNotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@FeignClient(value = "order-service", url = "http://localhost:8081/order-items")
@FeignClient(name = "order-service")

public interface OrderItemClient {

    @PostMapping
    OrderItemDto CreateOrderItem(@RequestBody OrderItemDto orderItem) throws OrderItemNotFoundException;

    @PostMapping
    OrderItemDto updateOrderItem(@RequestBody OrderItemDto orderItem) throws OrderItemNotFoundException;


}
