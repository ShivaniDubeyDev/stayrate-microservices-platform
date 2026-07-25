package com.stayrate.user.external.client;

import com.stayrate.user.dto.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

    @PostMapping("/ratings")
    ResponseEntity<Rating> createRating(@RequestBody Rating rating);

    @GetMapping("/ratings/{ratingId}")
    Rating getRating(@PathVariable("ratingId") String ratingId);

    @GetMapping("/ratings")
    List<Rating> getAllRatings();

    @GetMapping("/ratings/users/{userId}")
    List<Rating> getRatingsByUserId(@PathVariable("userId") String userId);

    @PutMapping("/ratings/{ratingId}")
    ResponseEntity<Rating> updateRating(@PathVariable("ratingId") String ratingId, @RequestBody Rating rating);

    @DeleteMapping("/ratings/{ratingId}")
    void deleteRating(@PathVariable("ratingId") String ratingId);
}