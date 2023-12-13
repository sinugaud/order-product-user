package com.javapedia.productify.service;

import com.javapedia.productify.client.OrderItemClient;
import com.javapedia.productify.exeptions.OrderItemNotFoundException;
import com.javapedia.productify.dto.OrderItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemDelegatesServiceImpl implements OrderItemDelegateService {

    @Autowired
    private OrderItemClient orderItemClient;

    @Override
    public OrderItemDto createOrderItem(OrderItemDto orderItemDto) throws OrderItemNotFoundException {
        return orderItemClient.CreateOrderItem(orderItemDto);
    }

    @Override
    public OrderItemDto updateOrderItem(OrderItemDto orderItemDto) throws OrderItemNotFoundException {
        return orderItemClient.updateOrderItem(orderItemDto);
    }
}
