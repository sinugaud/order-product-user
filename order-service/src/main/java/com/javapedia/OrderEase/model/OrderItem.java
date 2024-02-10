package com.javapedia.OrderEase.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


     private Long productId;

    private Double quantity;

    private double price;
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore // Add this annotation to break the circular reference

    private Order order;

}
