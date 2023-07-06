package com.example.demo01.repositories;

import com.example.demo01.models.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person,String> {
}
