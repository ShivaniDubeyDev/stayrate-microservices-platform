package com.stayrate.rating.service;

import com.stayrate.rating.dto.RatingDto;
import java.util.List;

public interface RatingService {

    // Create
    RatingDto create(RatingDto ratingDto);

    // Read Single
    RatingDto getRating(String ratingId);

    // Read All
    List<RatingDto> getRatings();

    // Read All by UserId
    List<RatingDto> getRatingByUserId(String userId);

    // Read All by HotelId
    List<RatingDto> getRatingByHotelId(String hotelId);

    // Update
    RatingDto updateRating(String ratingId, RatingDto ratingDto);

    // Delete
    void deleteRating(String ratingId);
}