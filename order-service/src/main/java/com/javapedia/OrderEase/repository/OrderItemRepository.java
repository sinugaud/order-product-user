package com.javapedia.OrderEase.repository;

import com.javapedia.OrderEase.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
