package com.javapedia.OrderEase.repository;

import com.javapedia.OrderEase.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long>
{


}
