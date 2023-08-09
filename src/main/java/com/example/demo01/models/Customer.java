package com.example.demo01.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("customers")
public class Customer {
    @Id
    private String id;
    private String username;
    private String password;
    private String role;
    private String fullname;
    private String img;
    private String uidFb;

    public Customer(){

    }

    public Customer(String id, String username, String password, String role, String fullname, String img, String uidFb) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullname = fullname;
        this.img = img;
        this.uidFb = uidFb;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUidFb() {
        return uidFb;
    }

    public void setUidFb(String uidFb) {
        this.uidFb = uidFb;
    }
}
