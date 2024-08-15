package com.javapedia.OrderEase.config;

import com.javapedia.OrderEase.dto.Product;
import com.javapedia.OrderEase.fallback.ProductFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//@FeignClient(value = "product-service", url = "http://localhost:8082/api/products/" ,fallback = ProductFallbackService.class)
@FeignClient( name= "product-service", fallback = ProductFallbackService.class, configuration = FeignClientConfig.class)
public interface ProductClient {

    @GetMapping("/api/products/")
    List<Product> getAllProducts();


    @GetMapping("/api/products/{id}")
    Product getProductById(@PathVariable Long id);


//    @PostMapping("/api/products/")
//     Product createProduct(@RequestBody Product product);


    @DeleteMapping("/api/products/{id}")
    String deleteProductById(@PathVariable Long id);


}