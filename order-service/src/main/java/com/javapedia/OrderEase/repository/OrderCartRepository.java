package com.javapedia.OrderEase.repository;

import com.javapedia.OrderEase.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCartRepository extends JpaRepository<Cart,Long> {
}
