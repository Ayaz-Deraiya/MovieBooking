package org.moviebooking.apigateway.Security.controller;


import org.moviebooking.apigateway.Security.DTO.LoginUser;
import org.moviebooking.apigateway.Security.Service.LoginSer;
import org.moviebooking.apigateway.Security.entity.User;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    private final LoginSer service;

    public UserController(LoginSer service) {
        this.service = service;
    }

    
    @PostMapping("/sign-up")
    public ResponseEntity<?> register(@RequestBody User user) {
        return service.register(user);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUser loginUser) {
        return service.login(loginUser);
    }


    @GetMapping("/validate")
    public ResponseEntity<?> validate() {
        return service.validateToken();
    }
}
