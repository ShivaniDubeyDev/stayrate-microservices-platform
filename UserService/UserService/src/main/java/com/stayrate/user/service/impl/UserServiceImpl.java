package com.stayrate.user.service.impl;

import com.stayrate.user.dto.Hotel;
import com.stayrate.user.dto.Rating;
import com.stayrate.user.entity.User;
import com.stayrate.user.exception.ResourceNotFoundException;
import com.stayrate.user.external.client.HotelService;
import com.stayrate.user.external.client.RatingService;
import com.stayrate.user.repository.UserRepository;
import com.stayrate.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RatingService ratingService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    // 1. CREATE USER
    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    // 2. GET ALL USERS
    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    // 3. GET SINGLE USER (With Ratings and Hotel Info)
    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server !! : " + userId));

        // Fetch user ratings using RatingService Feign Client
        List<Rating> ratingsOfUser = ratingService.getRatingsByUserId(user.getUserId());

        List<Rating> ratingList = ratingsOfUser.stream().map(rating -> {
            // Fetch hotel details for each rating using HotelService Feign Client
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;
    }

    // 4. UPDATE USER
    @Override
    public User updateUser(String userId, User user) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server !! : " + userId));

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setAbout(user.getAbout());

        return userRepository.save(existingUser);
    }

    // 5. DELETE USER
    @Override
    public void deleteUser(String userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server !! : " + userId));

        userRepository.delete(existingUser);
    }
}