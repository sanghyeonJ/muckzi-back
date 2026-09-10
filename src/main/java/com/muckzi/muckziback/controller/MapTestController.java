package com.muckzi.muckziback.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "http://localhost:5173")
public class MapTestController {

    @GetMapping
    public List<Map<String, Object>> getMockPlace() {
        List<Map<String, Object>> places = new ArrayList<>();

        // 💡 1번째 가짜 맛집 (서울시청 근처)
        Map<String, Object> place1 = new HashMap<>();
        place1.put("id", 1);
        place1.put("name", "시청역 찐 맛집 분식");
        place1.put("category", "한식");
        place1.put("lat", 37.5665);
        place1.put("lng", 126.9780);
        places.add(place1);

        // 💡 2번째 가짜 맛집 (덕수궁 근처)
        Map<String, Object> place2 = new HashMap<>();
        place2.put("id", 2);
        place2.put("name", "덕수궁 돌담길 카페");
        place2.put("category", "카페");
        place2.put("lat", 37.5658);
        place2.put("lng", 126.9751);
        places.add(place2);

        // 💡 3번째 가짜 맛집 (서울광장 옆)
        Map<String, Object> place3 = new HashMap<>();
        place3.put("id", 3);
        place3.put("name", "시청 스시 맛집");
        place3.put("category", "일식");
        place3.put("lat", 37.5672);
        place3.put("lng", 126.9795);
        places.add(place3);

        return places;

    }

}
