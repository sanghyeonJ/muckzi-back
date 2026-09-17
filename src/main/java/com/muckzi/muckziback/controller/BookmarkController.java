package com.muckzi.muckziback.controller;

import com.muckzi.muckziback.dto.BookmarkRequest;
import com.muckzi.muckziback.entity.Place;
import com.muckzi.muckziback.repository.BookmarkRepository;
import com.muckzi.muckziback.service.BookmarkService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkRepository bookmarkRepository;
    private final BookmarkService bookmarkService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{placeId}/bookmark")
    public Map<String, Boolean> getBookmark(
            @PathVariable Long placeId,
            Authentication authentication
    ) {
        String userId = authentication.getName();

        boolean bookmarked = bookmarkRepository
                .findByUser_UserIdAndPlace_PlaceId(userId, placeId)
                .isPresent();

        return Map.of("bookmarked", bookmarked);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/bookmark")
    public Map<String, Long> createBookmark(
            @Valid @RequestBody BookmarkRequest request,
            Authentication authentication
    ) {
        String userId = authentication.getName();

        Place place = bookmarkService.createBookmark(userId, request);
        return Map.of("placeId", place.getPlaceId());
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("{placeId}/bookmark")
    public void deleteBookmark(
            @PathVariable Long placeId,
            Authentication authentication
    ) {
        String userId = authentication.getName();

        bookmarkService.deleteBookmark(userId, placeId);
    }

}
