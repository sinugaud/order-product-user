package com.javapedia.productify.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;

    private String username;

    private LocalDate date = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private double totalAmount;

    private List<OrderItem> orderItems;

}
