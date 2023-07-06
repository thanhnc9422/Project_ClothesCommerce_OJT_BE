package com.example.demo01.controller;

import com.example.demo01.models.Shoe;
import com.example.demo01.repositories.ShoeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ShoeController {
    @Autowired
    ShoeRepository repository;
    @GetMapping("/AllShoes")
    ResponseEntity getAllShoes(){
        List<Shoe> listShoe = repository.findAll();
        return ResponseEntity.ok(listShoe);
    }
    @GetMapping("/getShoe")
    Optional<Shoe> getShoe(){
        Optional<Shoe> shoeOptional = repository.findById("649464f8f81a903697c81340");
            return shoeOptional;
    }
    @PutMapping("/updateShoe/{id}")
    ResponseEntity<String> updateShoe(@PathVariable String id,  @RequestParam("name") String name, @RequestParam("price") String price){
        Optional<Shoe> shoeOptional = repository.findById(id);
        if(shoeOptional.isPresent()){
            Shoe shoe = shoeOptional.get();
            shoe.setName(name);
            shoe.setPrice(price);
            repository.save(shoe);
            return ResponseEntity.ok("Shoe updated successfully");
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
