package com.stayrate.rating.controller;

import com.stayrate.rating.dto.ApiResponse;
import com.stayrate.rating.dto.RatingDto;
import com.stayrate.rating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    // 1. CREATE RATING
    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping
    public ResponseEntity<RatingDto> create(@RequestBody RatingDto ratingDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.create(ratingDto));
    }

    // 2. GET SINGLE RATING
    @GetMapping("/{ratingId}")
    public ResponseEntity<RatingDto> getRating(@PathVariable String ratingId) {
        return ResponseEntity.ok(ratingService.getRating(ratingId));
    }

    // 3. GET ALL RATINGS
    @GetMapping
    public ResponseEntity<List<RatingDto>> getRatings() {
        return ResponseEntity.ok(ratingService.getRatings());
    }

    // 4. GET ALL RATINGS OF USER
    @PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<RatingDto>> getRatingsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(ratingService.getRatingByUserId(userId));
    }

    // 5. GET ALL RATINGS OF HOTEL
    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<RatingDto>> getRatingsByHotelId(@PathVariable String hotelId) {
        return ResponseEntity.ok(ratingService.getRatingByHotelId(hotelId));
    }

    // 6. UPDATE RATING
    @PutMapping("/{ratingId}")
    public ResponseEntity<RatingDto> updateRating(@PathVariable String ratingId, @RequestBody RatingDto ratingDto) {
        return ResponseEntity.ok(ratingService.updateRating(ratingId, ratingDto));
    }

    // 7. DELETE RATING
    @DeleteMapping("/{ratingId}")
    public ResponseEntity<ApiResponse> deleteRating(@PathVariable String ratingId) {
        ratingService.deleteRating(ratingId);
        ApiResponse response = ApiResponse.builder()
                .message("Rating deleted successfully !!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.ok(response);
    }
}