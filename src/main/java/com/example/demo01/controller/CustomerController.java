package com.example.demo01.controller;

import com.example.demo01.models.Customer;
import com.example.demo01.models.JwtRequest;
import com.example.demo01.models.JwtResponse;
import com.example.demo01.repositories.CustomerRepository;
import com.example.demo01.services.CustomerService;
import com.example.demo01.util.Crypto;
import com.example.demo01.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CustomerController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    Crypto crypto;
    @Autowired
    CustomerService customerService;
    @GetMapping("/getAllCustomers")
    public ResponseEntity getAllCustomers(){
        List<Customer> customerList = customerRepository.findAll();
       return   ResponseEntity.ok(customerList);
    }
    @PostMapping("/login")
    public ResponseEntity customerLogin(@RequestBody JwtRequest authenticationRequest, HttpServletResponse response){
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        Customer customer = new Customer();
        try {
            customer = customerRepository.findOneByUsernameAndPassword(username,crypto.encrypt(password));
            if(customer == null){
               return ResponseEntity.ok("NOT FOUND");
            }else {
                try {
                    authenticate(username, password);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }catch (Exception e){
            return ResponseEntity.ok(e.toString());
        }
        final String token = jwtTokenUtil.generateToken(customer);

//        Cookie cookie = new Cookie("jwt",token);
//        cookie.setHttpOnly(true);
//        cookie.setSecure(true);
//        cookie.setPath("/");
//        response.addCookie(cookie);
        ResponseCookie cookie = ResponseCookie.from("jwt",token) // key & value
                .secure(true).httpOnly(true)
                    .path("/")
                .sameSite("None")
                .domain(null)
                .build()
                ;
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok().body(customer);
    }
    @PostMapping("/logout")
    public ResponseEntity customerLogout(HttpServletRequest request) {
        return ResponseEntity.ok().body("ok");
    }

    private void authenticate(String username, String password) throws Exception {
        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
