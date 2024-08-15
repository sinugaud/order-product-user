package com.javapedia.OrderEase.service.productdelegate;

import com.javapedia.OrderEase.config.ProductClient;
import com.javapedia.OrderEase.dto.Product;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
    @CircuitBreaker(name = "productClient", fallbackMethod = "getDefaultProductById")

    public Product getProductById(Long id) {
        return productClient.getProductById(id);
    }

    private Product getDefaultProductById(Long id, Throwable throwable) {
        // Fallback logic
        return new Product(id, "Unavailable Product", "No description available", 0.0);
    }
}


