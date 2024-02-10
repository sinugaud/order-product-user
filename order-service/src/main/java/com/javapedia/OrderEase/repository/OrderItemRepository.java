package com.javapedia.OrderEase.repository;

import com.javapedia.OrderEase.model.Order;
import com.javapedia.OrderEase.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    List<OrderItem> findByOrder(Order order);
}
