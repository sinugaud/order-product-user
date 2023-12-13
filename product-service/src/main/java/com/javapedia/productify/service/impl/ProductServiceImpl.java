package com.javapedia.productify.service.impl;

import com.javapedia.productify.dto.OrderItemDto;
import com.javapedia.productify.exeptions.OrderItemNotFoundException;
import com.javapedia.productify.exeptions.ResourceNotFoundException;
import com.javapedia.productify.model.Product;
import com.javapedia.productify.repository.ProductRepository;
import com.javapedia.productify.service.OrderItemDelegateService;
import com.javapedia.productify.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    OrderItemDelegateService orderItemDelegateService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }


    @Override
    public Product createProduct(Product product) throws OrderItemNotFoundException {
        Product saveProduct = productRepository.save(product);
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setProductId(product.getId());
        orderItemDto.setQuantity(product.getQuantity());
        orderItemDelegateService.updateOrderItem(orderItemDto);

        return saveProduct;
    }

    @Override
    public void deleteProductById(Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow();

        productRepository.delete(existingProduct);
    }
}
