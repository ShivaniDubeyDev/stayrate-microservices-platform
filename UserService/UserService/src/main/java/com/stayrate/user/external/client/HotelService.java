package com.stayrate.user.external.client;

import com.stayrate.user.dto.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelService {

    @PostMapping("/hotels")
    ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel);

    @GetMapping("/hotels/{hotelId}")
    Hotel getHotel(@PathVariable("hotelId") String hotelId);

    @GetMapping("/hotels")
    List<Hotel> getAllHotels();

    @PutMapping("/hotels/{hotelId}")
    ResponseEntity<Hotel> updateHotel(@PathVariable("hotelId") String hotelId, @RequestBody Hotel hotel);

    @DeleteMapping("/hotels/{hotelId}")
    void deleteHotel(@PathVariable("hotelId") String hotelId);
}