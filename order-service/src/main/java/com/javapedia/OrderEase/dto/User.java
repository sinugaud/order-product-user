package com.javapedia.OrderEase.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.Set;


@Data
public class User {
    private Integer id;
    private String name;
    private String username;
    private String email;
    private String password;
    private Set<Role> roles;
}
  