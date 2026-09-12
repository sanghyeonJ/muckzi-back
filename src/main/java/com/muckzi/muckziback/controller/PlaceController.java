package com.muckzi.muckziback.controller;

import com.muckzi.muckziback.dto.PlaceResponse;
import com.muckzi.muckziback.entity.Place;
import com.muckzi.muckziback.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceRepository placeRepository;

    @GetMapping
    public List<PlaceResponse> getPlaces() {
        List<Place> places = placeRepository.findAll();

        return places.stream()
                .filter(place -> place.getStatus() == Place.Status.ACTIVE)
                .map(PlaceResponse::new)
                .toList();
    }

}
