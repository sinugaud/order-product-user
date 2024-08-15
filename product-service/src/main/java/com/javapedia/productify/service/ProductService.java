 package com.javapedia.productify.service;

 import com.javapedia.productify.exeptions.OrderItemNotFoundException;
 import com.javapedia.productify.model.Product;
 import com.javapedia.productify.exeptions.InsufficientProductQuantityException;

 import java.util.List;

 public interface ProductService {

     List<Product> getAllProducts();
     Product getProductById(Long id);
     Product addProduct(Product product) throws OrderItemNotFoundException;
     void deleteProductById(Long id);
      Product updateProduct(Product product, Long id) ;


         void processOrderPlacedEvent(Long orderId) throws InsufficientProductQuantityException;
 }
