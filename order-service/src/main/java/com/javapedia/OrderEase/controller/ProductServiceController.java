package com.javapedia.OrderEase.controller;

import com.javapedia.OrderEase.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/orders/product")
public class ProductServiceController {
    @Autowired
    private ProductClient productClient;


    @GetMapping("/product")
    public ResponseEntity<List<Product>> getAllproduct() {
        List<Product> products = productClient.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
