package com.javapedia.OrderEase.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javapedia.OrderEase.Enum.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "table_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userId;

    private LocalDate date = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private double totalAmount;

    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

}
