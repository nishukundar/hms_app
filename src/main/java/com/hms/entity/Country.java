package com.hms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "country_name", nullable = false)
    private String name;

//    @OneToMany(mappedBy = "country", cascade=CascadeType.REMOVE, orphanRemoval = true)
//    private Set<Property> properties;

}