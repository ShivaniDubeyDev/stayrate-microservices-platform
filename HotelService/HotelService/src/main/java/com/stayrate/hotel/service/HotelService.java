package com.stayrate.hotel.service;

import com.stayrate.hotel.dto.HotelDto;
import java.util.List;

public interface HotelService {

    // Create
    HotelDto create(HotelDto hotelDto);

    // Get All
    List<HotelDto> getAll();

    // Get Single
    HotelDto get(String id);

    // Update
    HotelDto update(String id, HotelDto hotelDto);

    // Delete
    void delete(String id);
}