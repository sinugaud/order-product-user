package com.javapedia.OrderEase.entity;

import com.javapedia.OrderEase.Enum.AddressType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userId;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    private String street;

    private String city;

    private String state;

    private String country;

    private String postalCode;

}
