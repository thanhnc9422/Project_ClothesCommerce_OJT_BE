package com.example.demo01.controller;

import com.example.demo01.models.Person;
import com.example.demo01.repositories.PersonRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {
    @Autowired
    PersonRepository repository;
    @GetMapping("/test")
    ResponseEntity test(){
         List<Person> listPerson = repository.findAll();
        return ResponseEntity.ok(listPerson);
    }
}
