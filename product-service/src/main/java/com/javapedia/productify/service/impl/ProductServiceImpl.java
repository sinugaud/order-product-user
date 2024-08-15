package com.javapedia.productify.service.impl;

import com.javapedia.productify.client.OrderServiceClient;
import com.javapedia.productify.dto.OrderDTO;
import com.javapedia.productify.dto.OrderItem;
import com.javapedia.productify.exeptions.InsufficientProductQuantityException;
import com.javapedia.productify.exeptions.OrderItemNotFoundException;
import com.javapedia.productify.exeptions.ProductNotFoundException;
import com.javapedia.productify.exeptions.ResourceNotFoundException;
import com.javapedia.productify.model.Product;
import com.javapedia.productify.repository.ProductRepository;
import com.javapedia.productify.service.OrderItemDelegateService;
import com.javapedia.productify.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    OrderItemDelegateService orderItemDelegateService;
        @Autowired
    private OrderServiceClient orderServiceClient;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Cacheable(value = "products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Cacheable(value = "products", key = "#id")

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }


    @Override
    @CachePut(value = "products", key = "#id")

    public Product addProduct(Product product) throws OrderItemNotFoundException {
        return productRepository.save(product);
    }

    @Override
    @CacheEvict(value = "products", key = "#id")
    public void deleteProductById(Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow();

        productRepository.delete(existingProduct);
    }

    @CachePut(value = "products", key = "#product.id")
    public Product updateProduct(Product product, Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
//        existingProduct.setCategory(product.getCategory());

        return productRepository.save(existingProduct);

    }


    @Override
    public void processOrderPlacedEvent(Long orderId) throws InsufficientProductQuantityException {
        // Fetch order details from an OrderDTO service through API or event
        log.info("order place event");
        OrderDTO orderDto = orderServiceClient.getOrderById(orderId);
        log.info("Order dto :{}", orderDto);

        // Process order items and update products
        for (OrderItem orderItem : orderDto.getOrderItems()) {
            Long productId = orderItem.getProductId();
            Double quantity = orderItem.getQuantity();

            // Fetch product details from your ProductService
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id ,{}" + productId));
            log.info("product  from event find by id {},", product);
            // Update the product quantity or perform any other relevant action
            double remainingQuantity = (product.getQuantity() - quantity);
            log.info(" quantity {}", quantity);

            //            if (remainingQuantity >= 0) {
            product.setQuantity(remainingQuantity);
            log.info("remaining quantity {}", remainingQuantity);

            productRepository.save(product);
            log.info("product update Successfully {}", product.getId());
            //            } else {
            //                // Handle insufficient quantity scenario
            //                // You might also want to implement compensation actions if needed
            //                log.info("product not update Successfully {}",product.getId());

            throw new InsufficientProductQuantityException("Insufficient quantity for product with id " + productId);
            //            }
        }

    }

}
