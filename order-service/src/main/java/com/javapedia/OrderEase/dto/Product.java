package com.javapedia.OrderEase.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Data
public class Product {
    private Long id;
    private String name ;
    private String description;
    private double price;
//    private Long cartId.;

    public Product(Long id, String unavailableProduct) {
    }
}
