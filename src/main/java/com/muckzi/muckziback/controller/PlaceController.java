package com.muckzi.muckziback.controller;

import com.muckzi.muckziback.dto.PlaceResponse;
import com.muckzi.muckziback.entity.Place;
import com.muckzi.muckziback.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceRepository placeRepository;

    @GetMapping
    public List<PlaceResponse> getPlaces(
            @RequestParam Double swLat,
            @RequestParam Double swLng,
            @RequestParam Double neLat,
            @RequestParam Double neLng
    ) {
        List<Place> places = placeRepository.findByLatitudeBetweenAndLongitudeBetween(swLat,neLat,swLng,neLng);

        return places.stream()
                .filter(place -> place.getStatus() == Place.Status.ACTIVE)
                .map(PlaceResponse::new)
                .toList();
    }

    @GetMapping("/{placeId}")
    public PlaceResponse getPlace(@PathVariable Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("음식점을 찾을 수 없습니다."));

        return new PlaceResponse(place);
    }

}
