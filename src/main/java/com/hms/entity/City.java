package com.hms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "city_name", nullable = false)
    private String name;

//    @OneToMany(mappedBy = "city", cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private Set<Property> properties;


}