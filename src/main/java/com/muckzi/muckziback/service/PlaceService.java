package com.muckzi.muckziback.service;

import com.muckzi.muckziback.entity.Place;
import com.muckzi.muckziback.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    @Transactional
    public Place findOrCreatePlace(
            String placeName,
            String category,
            String address,
            Double latitude,
            Double longitude
    ) {

        Optional<Place> existingPlace =
                placeRepository.findByPlaceNameAndAddress(placeName, address);

        if (existingPlace.isPresent()) {
            return existingPlace.get();
        }

        Place place = Place.builder()
                .placeName(placeName)
                .category(category)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .build();

        return placeRepository.save(place);
    }

}
