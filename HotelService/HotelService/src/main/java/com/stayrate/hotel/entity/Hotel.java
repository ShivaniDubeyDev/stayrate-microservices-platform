package com.stayrate.hotel.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "micro_hotels")
public class Hotel {

    @Id
    @Column(name = "hotel_id") 
    private String id;         
    private String name;
    private String location;
    private String about;
}