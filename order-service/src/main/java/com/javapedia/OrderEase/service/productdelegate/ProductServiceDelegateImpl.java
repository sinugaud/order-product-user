package com.javapedia.OrderEase.service.productdelegate;

import com.javapedia.OrderEase.controller.ProductClient;
import com.javapedia.OrderEase.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceDelegateImpl implements ProductServiceDelegate {
    @Autowired
    private ProductClient productClient;

    @Override
    public List<Product> getAllProductAndOrder() {
        return productClient.getAllProducts();
    }

    @Override
    public Product getProductById(Long id) {
        return productClient.getProductById(id);
    }
}


