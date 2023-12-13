package com.javapedia.OrderEase.service;

import com.javapedia.OrderEase.OrderNotFoundException;
import com.javapedia.OrderEase.controller.OrderItemNotFoundException;
import com.javapedia.OrderEase.model.OrderItem;
import com.javapedia.OrderEase.service.implement.ProductNotFoundException;

import java.util.List;

public interface OrderItemService {
      OrderItem updateOrderItem(OrderItem updatedOrderItem) throws OrderItemNotFoundException ;

      List<OrderItem> getAllOrderItem() throws OrderNotFoundException, OrderItemNotFoundException;
      OrderItem createOrderItem(OrderItem orderItem);

}
