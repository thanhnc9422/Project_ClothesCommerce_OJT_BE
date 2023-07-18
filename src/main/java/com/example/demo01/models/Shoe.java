package com.example.demo01.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Document("shoes")
@JsonIgnoreProperties({"_class"})
public class Shoe {
    @Id
    private String id;
    private String src;
    private String name;

    private String price;
    public Shoe(){

    }

    public Shoe(String id, String name, String src, String price) {
        this.id = id;
        this.name = name;
        this.src = src;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
