package com.javapedia.productify.service;

import com.javapedia.productify.exeptions.OrderItemNotFoundException;
import com.javapedia.productify.model.Product;
import com.javapedia.productify.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product createProduct(Product product) throws OrderItemNotFoundException;
    void deleteProductById(Long id);

}
