package com.muckzi.muckziback.service;

import com.muckzi.muckziback.entity.Bookmark;
import com.muckzi.muckziback.entity.Place;
import com.muckzi.muckziback.entity.User;
import com.muckzi.muckziback.repository.BookmarkRepository;
import com.muckzi.muckziback.repository.PlaceRepository;
import com.muckzi.muckziback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;

    @Transactional
    public void createBookmark(
            String userId,
            Long placeId
    ) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("음식점을 찾을 수 없습니다."));

        if(place.getStatus() != Place.Status.ACTIVE){
            throw new IllegalArgumentException("삭제된 음식점은 북마크할 수 없습니다.");
        }

        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .place(place)
                .build();

        bookmarkRepository.save(bookmark);
    }


    @Transactional
    public void deleteBookmark(
            String userId,
            Long placeId
    ) {
        Bookmark bookmark = bookmarkRepository
                .findByUser_UserIdAndPlace_PlaceId(userId, placeId)
                .orElseThrow(() -> new IllegalArgumentException("북마크를 찾을 수 없습니다."));

        bookmarkRepository.delete(bookmark);
    }

}
