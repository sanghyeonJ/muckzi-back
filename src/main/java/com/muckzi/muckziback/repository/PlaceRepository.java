package com.muckzi.muckziback.repository;

import com.muckzi.muckziback.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    List<Place> findByLatitudeBetweenAndLongitudeBetween(
            Double swLat,
            Double neLat,
            Double swLng,
            Double neLng
    );

    Optional<Place> findByPlaceNameAndAddress(
            String placeName,
            String address
    );

}
