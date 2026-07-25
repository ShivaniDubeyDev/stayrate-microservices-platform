package com.stayrate.user.controller;

import com.stayrate.user.dto.ApiResponse;
import com.stayrate.user.entity.User;
import com.stayrate.user.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    // 1. CREATE USER
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // 2. GET SINGLE USER
    @GetMapping("/{userId}")
    // @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    // @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId) {
        logger.info("Get Single User Handler: UserController");
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    // 3. GET ALL USERS
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUser();
        return ResponseEntity.ok(allUsers);
    }

    // 4. UPDATE USER
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody User user) {
        User updatedUser = userService.updateUser(userId, user);
        return ResponseEntity.ok(updatedUser);
    }

    // 5. DELETE USER
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        ApiResponse response = ApiResponse.builder()
                .message("User deleted successfully !!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.ok(response);
    }

    // Fallback Method for Resilience4J RateLimiter / CircuitBreaker / Retry
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex) {
        logger.error("Fallback executed due to exception: ", ex);
        User user = User.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("This dummy user is generated because downstream service is unavailable.")
                .userId("141234")
                .build();
        return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
    }
}