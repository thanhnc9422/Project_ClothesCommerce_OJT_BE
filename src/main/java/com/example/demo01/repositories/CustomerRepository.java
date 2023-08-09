package com.example.demo01.repositories;

import com.example.demo01.models.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    Customer findOneByUsername(String username);

    public Customer findOneByUsernameAndPassword(String username, String password);
}
