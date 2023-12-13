package com.javapedia.OrderEase.service.productdelegate;

import com.javapedia.OrderEase.dto.Product;

import java.util.List;

public interface ProductServiceDelegate {
     List<Product> getAllProductAndOrder();

    Product getProductById(Long id);

}
