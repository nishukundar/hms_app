package com.hms.repository;

import java.util.*;
import com.hms.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    public void deleteByCityId(Long cityId);
    public void deleteByCountryId(Long countryId);

    @Query("select p from Property p JOIN p.city c JOIN p.country co where c.name=:name or co.name=:name")
    public List<Property> searchHotels(@Param("name") String name);

}