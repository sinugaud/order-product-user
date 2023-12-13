package com.javapedia.productify.service;

import com.javapedia.productify.exeptions.OrderItemNotFoundException;
import com.javapedia.productify.dto.OrderItemDto;

public interface OrderItemDelegateService {
    OrderItemDto createOrderItem(OrderItemDto orderItemDto) throws OrderItemNotFoundException;
    OrderItemDto updateOrderItem(OrderItemDto orderItemDto) throws OrderItemNotFoundException;


}
