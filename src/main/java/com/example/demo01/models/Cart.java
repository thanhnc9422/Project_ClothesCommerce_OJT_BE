package com.example.demo01.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document("carts")
public class Cart {
    @Id
    private String id;
    private String userId;
    private List<Shoe> shoes;
    private List<String> address;

    public Cart(){

    }

    public Cart(String id, String userId, List<Shoe> shoes, List<String> address) {
        this.id = id;
        this.userId = userId;
        this.shoes = shoes;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Shoe> getShoes() {
        return shoes;
    }

    public void setShoes(List<Shoe> shoes) {
        this.shoes = shoes;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }
}
