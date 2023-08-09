package com.example.demo01.controller;

import com.example.demo01.models.Cart;
import com.example.demo01.models.Shoe;
import com.example.demo01.repositories.CartRepository;
import com.example.demo01.repositories.ShoeRepository;
import com.example.demo01.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ShoeController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    ShoeRepository shoeRepository;
    @Autowired
    CartRepository cartRepository;

    @GetMapping("/hello")
    ResponseEntity<String> hello() {
        return ResponseEntity.ok("nice to meet u <3");
    }

    @GetMapping("/AllShoes")
    ResponseEntity getAllShoes() {
        List<Shoe> listShoe = shoeRepository.findAll();
        return ResponseEntity.ok(listShoe);
    }

    @GetMapping("/getShoe")
    Optional<Shoe> getShoe() {
        Optional<Shoe> shoeOptional = shoeRepository.findById("649464f8f81a903697c81340");
        return shoeOptional;
    }

    @PostMapping("/addShoe")
    ResponseEntity<String> createShoe(@RequestParam String name, @RequestParam String src, @RequestParam String price, HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Cookie");
        String username = null;
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("jwt=")) {
            jwtToken = requestTokenHeader.substring(4);
            try {
                String role = jwtTokenUtil.getUserRoleFromToken(jwtToken);
                if (role.equals("Admin")) {
                    Shoe createShoe = new Shoe();
                    createShoe.setPrice(price);
                    createShoe.setName(name);
                    createShoe.setSrc(src);
                    shoeRepository.save(createShoe);
                    return ResponseEntity.ok("create a new shoe successfully!");
                }
            } catch (Exception e) {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok("NOT AUTHORIZATION");
    }

    @DeleteMapping("/deleteShoe/{id}")
    ResponseEntity<String> deleteShoe(@PathVariable String id, HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Cookie");
        String username = null;
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("jwt=")) {
            jwtToken = requestTokenHeader.substring(4);
            try {
                String role = jwtTokenUtil.getUserRoleFromToken(jwtToken);
                if (role.equals("Admin")) {
                    shoeRepository.deleteById(id);
                    return ResponseEntity.ok("delete a shoe successfully!");
                }
            } catch (Exception e) {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok("NOT AUTHORIZATION");
    }

    @PutMapping("/updateShoe/{id}")
    ResponseEntity<String> updateShoe(@PathVariable String id, @RequestParam("name") String name, @RequestParam("price") String price, HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Cookie");
        String username = null;
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("jwt=")) {
            jwtToken = requestTokenHeader.substring(4);
            try {
                String role = jwtTokenUtil.getUserRoleFromToken(jwtToken);
                if (role.equals("Admin")) {
                    Optional<Shoe> shoeOptional = shoeRepository.findById(id);
                    if (shoeOptional.isPresent()) {
                        Shoe shoe = shoeOptional.get();
                        shoe.setName(name);
                        shoe.setPrice(price);
                        shoeRepository.save(shoe);
                        return ResponseEntity.ok("UPDATE SUCCESSFULLY");
                    }
                }
            } catch (Exception e) {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok("NOT AUTHORIZATION");
    }

    @PostMapping("/cart/add/{userId}")
    ResponseEntity<String> addCart(@PathVariable String userId, @RequestParam("id") String id, @RequestParam("size") String size, HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Cookie");
        String username = null;
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("jwt=")) {
            jwtToken = requestTokenHeader.substring(4);
            try {
                String role = jwtTokenUtil.getUserRoleFromToken(jwtToken);
                if (role.equals("Admin")) {
                    Shoe shoe = shoeRepository.findOneById(id);
                    shoe.setSize(size);
                    Cart cart = cartRepository.findOneByUserId(userId);
                    if (cart == null) {
                        List<Shoe> shoes = new ArrayList<>();
                        shoes.add(shoe);
                        Cart cart1 = new Cart();
                        cart1.setUserId(userId);
                        cart1.setShoes(shoes);
                        cartRepository.save(cart1);
                        return ResponseEntity.ok("add shoe to new cart successfully!");
                    }

                    List<Shoe> list = cart.getShoes();
                    list.add(shoe);
                    cart.setShoes(list);
                    cartRepository.save(cart);
                    return ResponseEntity.ok("add shoe to cart successfully!");
                }
            } catch (Exception e) {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok("NOT AUTHORIZATION");
    }

    @GetMapping("/cart/view/{userId}")
    ResponseEntity<Cart> getCart(@PathVariable String userId, HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Cookie");
        String username = null;
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("jwt=")) {
            jwtToken = requestTokenHeader.substring(4);
            try {
                String role = jwtTokenUtil.getUserRoleFromToken(jwtToken);
                if (role.equals("Admin")) {
                    Cart cart = cartRepository.findOneByUserId(userId);
                    if (cart == null) {
                        Cart cart1 = new Cart();
                        List<Shoe> shoes = new ArrayList<>();
                        List<String> adress = new ArrayList<>();
                        cart1.setShoes(shoes);
                        cart1.setUserId(userId);
                        cart1.setAddress(adress);
                        cartRepository.save(cart1);
                        return ResponseEntity.ok(cart1);
                    }
//                    List<Shoe> list = cart.getShoes();
                    List<Shoe> list = cart.getShoes();
                    return ResponseEntity.ok(cart);
                }
            } catch (Exception e) {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(null);
    }

    @PostMapping("/cart/delete/{userId}")
    ResponseEntity<String> deleteCart(@PathVariable String userId, @RequestParam("id") String id, HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Cookie");
        String username = null;
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("jwt=")) {
            jwtToken = requestTokenHeader.substring(4);
            try {
                String role = jwtTokenUtil.getUserRoleFromToken(jwtToken);
                if (role.equals("Admin")) {
                    Cart cart = cartRepository.findOneByUserId(userId);
                    List<Shoe> list = cart.getShoes();
                    for (Shoe index : list) {
                        if (index.getId().equals(id)) {
                            list.remove(index);
                            cart.setShoes(list);
                            break;
                        }
                    }
                    cartRepository.save(cart);

                    return ResponseEntity.ok("DELETE SUCCESSFULLY");
                }
            } catch (Exception e) {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok("NOT AUTHORIZATION");
    }

    @PostMapping("/cart/add/address/{userId}")
    ResponseEntity<String> addNewAddress(@PathVariable String userId, @RequestParam("address") String address, HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Cookie");
        String username = null;
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("jwt=")) {
            jwtToken = requestTokenHeader.substring(4);
            try {
                String role = jwtTokenUtil.getUserRoleFromToken(jwtToken);
                if (role.equals("Admin")) {
                    Cart cart = cartRepository.findOneByUserId(userId);
                   List<String> list = cart.getAddress();
                   list.add(address);
                   cart.setAddress(list);
                    cartRepository.save(cart);
                    return ResponseEntity.ok("DELETE SUCCESSFULLY");
                    }
                return ResponseEntity.ok("DELETE FAILED");
            } catch (Exception e) {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok("NOT AUTHORIZATION");
    }
}
