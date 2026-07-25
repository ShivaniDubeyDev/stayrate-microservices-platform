package com.stayrate.hotel.service.impl;

import com.stayrate.hotel.dto.HotelDto;
import com.stayrate.hotel.entity.Hotel;
import com.stayrate.hotel.exception.ResourceNotFoundException;
import com.stayrate.hotel.repository.HotelRepository;
import com.stayrate.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    // Helper: Entity -> DTO
    private HotelDto mapToDto(Hotel hotel) {
        return new HotelDto(
                hotel.getId(),
                hotel.getName(),
                hotel.getLocation(),
                hotel.getAbout()
        );
    }

    // Helper: DTO -> Entity
    private Hotel mapToEntity(HotelDto dto) {
        return new Hotel(
                dto.getId(),
                dto.getName(),
                dto.getLocation(),
                dto.getAbout()
        );
    }

    @Override
    public HotelDto create(HotelDto hotelDto) {
        String hotelId = UUID.randomUUID().toString();
        hotelDto.setId(hotelId);
        
        Hotel hotel = mapToEntity(hotelDto);
        Hotel savedHotel = hotelRepository.save(hotel);
        return mapToDto(savedHotel);
    }

    @Override
    public List<HotelDto> getAll() {
        return hotelRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public HotelDto get(String id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel with given ID not found !! : " + id));
        return mapToDto(hotel);
    }

    @Override
    public HotelDto update(String id, HotelDto hotelDto) {
        Hotel existingHotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel with given ID not found !! : " + id));

        existingHotel.setName(hotelDto.getName());
        existingHotel.setLocation(hotelDto.getLocation());
        existingHotel.setAbout(hotelDto.getAbout());

        Hotel updatedHotel = hotelRepository.save(existingHotel);
        return mapToDto(updatedHotel);
    }

    @Override
    public void delete(String id) {
        Hotel existingHotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel with given ID not found !! : " + id));
        hotelRepository.delete(existingHotel);
    }
}