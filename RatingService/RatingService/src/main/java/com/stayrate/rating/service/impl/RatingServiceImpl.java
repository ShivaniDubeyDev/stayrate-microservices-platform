package com.stayrate.rating.service.impl;

import com.stayrate.rating.dto.RatingDto;
import com.stayrate.rating.entity.Rating;
import com.stayrate.rating.exception.ResourceNotFoundException;
import com.stayrate.rating.repository.RatingRepository;
import com.stayrate.rating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository repository;

    // Helper: Convert Entity -> DTO
    private RatingDto mapToDto(Rating rating) {
        return new RatingDto(
                rating.getRatingId(),
                rating.getUserId(),
                rating.getHotelId(),
                rating.getRating(),
                rating.getFeedback()
        );
    }

    // Helper: Convert DTO -> Entity
    private Rating mapToEntity(RatingDto dto) {
        return new Rating(
                dto.getRatingId(),
                dto.getUserId(),
                dto.getHotelId(),
                dto.getRating(),
                dto.getFeedback()
        );
    }

    @Override
    public RatingDto create(RatingDto ratingDto) {
        Rating rating = mapToEntity(ratingDto);
        Rating savedRating = repository.save(rating);
        return mapToDto(savedRating);
    }

    @Override
    public RatingDto getRating(String ratingId) {
        Rating rating = repository.findById(ratingId)
                .orElseThrow(() -> new ResourceNotFoundException("Rating with given ID not found on server !! : " + ratingId));
        return mapToDto(rating);
    }

    @Override
    public List<RatingDto> getRatings() {
        return repository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RatingDto> getRatingByUserId(String userId) {
        return repository.findByUserId(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RatingDto> getRatingByHotelId(String hotelId) {
        return repository.findByHotelId(hotelId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public RatingDto updateRating(String ratingId, RatingDto ratingDto) {
        Rating existingRating = repository.findById(ratingId)
                .orElseThrow(() -> new ResourceNotFoundException("Rating with given ID not found on server !! : " + ratingId));

        existingRating.setRating(ratingDto.getRating());
        existingRating.setFeedback(ratingDto.getFeedback());
        existingRating.setUserId(ratingDto.getUserId());
        existingRating.setHotelId(ratingDto.getHotelId());

        Rating updatedRating = repository.save(existingRating);
        return mapToDto(updatedRating);
    }

    @Override
    public void deleteRating(String ratingId) {
        Rating existingRating = repository.findById(ratingId)
                .orElseThrow(() -> new ResourceNotFoundException("Rating with given ID not found on server !! : " + ratingId));
        repository.delete(existingRating);
    }
}