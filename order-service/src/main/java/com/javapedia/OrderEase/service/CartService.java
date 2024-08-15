package com.javapedia.OrderEase.service;

import com.javapedia.OrderEase.entity.Cart;

import java.util.List;

public interface CartService {
    List<Cart> addToCart(List<Cart> cart) ;
    List<Cart> getAllCart( ) ;
    Boolean deleteCart(Long id ) ;


}
