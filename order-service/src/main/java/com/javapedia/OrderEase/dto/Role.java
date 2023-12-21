package com.javapedia.OrderEase.dto;

import jakarta.persistence.*;
import lombok.Data;


@Data
public class Role {

    private Integer id;
    private String name;
    public Role() {
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}