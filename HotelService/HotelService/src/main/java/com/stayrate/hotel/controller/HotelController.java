package com.stayrate.hotel.controller;

import com.stayrate.hotel.dto.ApiResponse;
import com.stayrate.hotel.dto.HotelDto;
import com.stayrate.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    // 1. CREATE HOTEL
    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping
    public ResponseEntity<HotelDto> createHotel(@RequestBody HotelDto hotelDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.create(hotelDto));
    }

    // 2. GET SINGLE HOTEL
    @PreAuthorize("hasAuthority('SCOPE_internal')")
    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelDto> getSingleHotel(@PathVariable String hotelId) {
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.get(hotelId));
    }

    // 3. GET ALL HOTELS
    @PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
    @GetMapping
    public ResponseEntity<List<HotelDto>> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAll());
    }

    // 4. UPDATE HOTEL
    @PreAuthorize("hasAuthority('Admin')")
    @PutMapping("/{hotelId}")
    public ResponseEntity<HotelDto> updateHotel(@PathVariable String hotelId, @RequestBody HotelDto hotelDto) {
        return ResponseEntity.ok(hotelService.update(hotelId, hotelDto));
    }

    // 5. DELETE HOTEL
    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("/{hotelId}")
    public ResponseEntity<ApiResponse> deleteHotel(@PathVariable String hotelId) {
        hotelService.delete(hotelId);
        ApiResponse response = ApiResponse.builder()
                .message("Hotel deleted successfully !!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.ok(response);
    }
}