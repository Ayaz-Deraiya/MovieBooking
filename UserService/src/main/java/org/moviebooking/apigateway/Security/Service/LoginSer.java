package org.moviebooking.apigateway.Security.Service;


import org.moviebooking.apigateway.Security.Config.JwtUtil;
import org.moviebooking.apigateway.Security.DTO.LoginUser;
import org.moviebooking.apigateway.Security.entity.User;
import org.moviebooking.apigateway.Security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoginSer{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private userService userService;

    public ResponseEntity<?> register(User user){
        if(userExists(user.getName())) return ResponseEntity.status(HttpStatus.OK).body("User Already exists");
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        String token=jwtUtil.generateToken(user.getName());
        return ResponseEntity.ok(token);
    }


    public ResponseEntity<?> login(LoginUser loginUser){
        try {
            authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword())
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        UserDetails userDetails = userService.loadUserByUsername(loginUser.getUsername());
        String token = jwtUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(token);
    }


    public boolean userExists(String name){
        return userRepository.findByName(name)!=null;
    }


    public ResponseEntity<?> validateToken(){
        return ResponseEntity.ok(true);
    }

}
