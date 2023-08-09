package com.example.demo01.repositories;

import com.example.demo01.models.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends MongoRepository<Cart,String> {
    public Cart findOneByUserId(String userId);
}
