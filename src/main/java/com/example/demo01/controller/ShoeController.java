package com.example.demo01.controller;

import com.example.demo01.models.JwtRequest;
import com.example.demo01.models.Shoe;
import com.example.demo01.repositories.ShoeRepository;
import com.example.demo01.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ShoeController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    ShoeRepository repository;

    @GetMapping("/hello")
    ResponseEntity<String> hello() {
        return ResponseEntity.ok("nice to meet u <3");
    }

    @GetMapping("/AllShoes")
    ResponseEntity getAllShoes() {
        List<Shoe> listShoe = repository.findAll();
        return ResponseEntity.ok(listShoe);
    }

    @GetMapping("/getShoe")
    Optional<Shoe> getShoe() {
        Optional<Shoe> shoeOptional = repository.findById("649464f8f81a903697c81340");
        return shoeOptional;
    }

    @PostMapping("/addShoe")
    ResponseEntity<String> createShoe(@RequestParam String name, @RequestParam String src, @RequestParam String price, HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                String role = jwtTokenUtil.getUserRoleFromToken(jwtToken);
                if (role.equals("Admin")) {
                    try {
                        Shoe createShoe = new Shoe();
                        createShoe.setPrice(price);
                        createShoe.setName(name);
                        createShoe.setSrc(src);
                        repository.save(createShoe);
                    } catch (Exception e) {
                        return ResponseEntity.notFound().build();
                    }
                    return ResponseEntity.ok("create a new shoe successfully!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        }
        return null;
    }


    @DeleteMapping("/deleteShoe/{id}")
    ResponseEntity<String> deleteShoe(@PathVariable String id) {
        try {
            System.out.println(id);
            repository.deleteById(id);
            return ResponseEntity.ok("delete a shoe successfully!");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateShoe/{id}")
    ResponseEntity<String> updateShoe(@PathVariable String id, @RequestParam("name") String name, @RequestParam("price") String price, HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Cookie");
        System.out.println("demo: "+requestTokenHeader);
        String username = null;
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                String role = jwtTokenUtil.getUserRoleFromToken(jwtToken);
                if (role.equals("Admin")) {
                    Optional<Shoe> shoeOptional = repository.findById(id);
                    if (shoeOptional.isPresent()) {
                        Shoe shoe = shoeOptional.get();
                        shoe.setName(name);
                        shoe.setPrice(price);
                        repository.save(shoe);
                        return ResponseEntity.ok("UPDATE SUCCESSFULLY");
                    }
                }
            } catch (Exception e) {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok("NOT AUTHORIZATION");

    }
}
