package com.javapedia.OrderEase.dto;

import com.javapedia.OrderEase.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    private Order order;
    private List<Product> products;


}
