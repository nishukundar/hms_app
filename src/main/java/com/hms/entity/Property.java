package com.hms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "no_of_guests", nullable = false)
    private Integer no_of_guests;

    @Column(name = "no_of_bathrooms", nullable = false)
    private Integer no_of_bathrooms;

    @Column(name = "no_of_rooms", nullable = false)
    private Integer no_of_rooms;

    @Column(name = "no_of_beds", nullable = false)
    private Integer no_of_beds;

    @Column(name = "hotel_name", nullable = false)
    private String hotelName;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;



}


