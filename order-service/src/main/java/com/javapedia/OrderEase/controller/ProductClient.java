package com.javapedia.OrderEase.controller;

import com.javapedia.OrderEase.dto.Product;
import com.javapedia.OrderEase.fallback.ProductFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@FeignClient(value = "product-service", url = "http://localhost:8082/api/products/" ,fallback = ProductFallbackService.class)
@FeignClient(value = "PRODUCT-SERVICE/api/products/",fallback = ProductFallbackService.class  )
public interface ProductClient {

    @GetMapping()
     List<Product> getAllProducts();


    @GetMapping("/{id}")
     Product getProductById(@PathVariable Long id);


//    @PostMapping
//     Product createProduct(@RequestBody Product product);


    @DeleteMapping("/{id}")
     String deleteProductById(@PathVariable Long id);


}