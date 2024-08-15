package com.javapedia.OrderEase.service.implement;

import com.javapedia.OrderEase.entity.Cart;
import com.javapedia.OrderEase.repository.OrderCartRepository;
import com.javapedia.OrderEase.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final OrderCartRepository orderCartRepository;

    @Autowired
    public CartServiceImpl(OrderCartRepository orderCartRepository) {
        this.orderCartRepository = orderCartRepository;
    }

    @Override
    public List<Cart> addToCart(List<Cart> cartItems) {
        return orderCartRepository.saveAll(cartItems);
    }

    @Override
    public List<Cart> getAllCart() {
        return orderCartRepository.findAll();
    }

    @Override
    public Boolean deleteCart(Long id) {
        Optional<Cart> cartItem = orderCartRepository.findById(id);

        if (cartItem.isPresent()) {
            orderCartRepository.deleteById(id);

            return true;
        } else {
            return false;
        }
    }
}


