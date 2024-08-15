package com.javapedia.OrderEase.service;

import com.javapedia.OrderEase.exeption.OrderNotFoundException;
import com.javapedia.OrderEase.exeption.OrderItemNotFoundException;
import com.javapedia.OrderEase.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
      OrderItem updateOrderItem(OrderItem updatedOrderItem) throws OrderItemNotFoundException ;

      List<OrderItem> getAllOrderItem() throws OrderNotFoundException, OrderItemNotFoundException;
      OrderItem createOrderItem(OrderItem orderItem);

}
