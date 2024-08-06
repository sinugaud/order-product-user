package com.javapedia.OrderEase.controller;

import com.javapedia.OrderEase.dto.Product;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/orders/product")
@Log4j2
public class ProductServiceController {
    @Autowired
    private ProductClient productClient;


    @GetMapping("/product")
    public ResponseEntity<List<Product>> getAllProduct() {
        List<Product> products = productClient.getAllProducts();
        log.info("Products from product service and product client:{} ",products);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
