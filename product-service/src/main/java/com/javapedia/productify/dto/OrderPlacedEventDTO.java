package com.javapedia.productify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderPlacedEventDTO implements Serializable {

    private Long orderId;

}
