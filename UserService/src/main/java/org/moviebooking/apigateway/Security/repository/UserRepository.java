package org.moviebooking.apigateway.Security.repository;


import org.moviebooking.apigateway.Security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByName(String username);
}
