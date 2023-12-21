package com.javapedia.OrderEase.repository;

import com.javapedia.OrderEase.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long>
{
    List<Order> findByUsername(String username);



}
