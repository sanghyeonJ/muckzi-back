package com.muckzi.muckziback.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@Service
public class SupabaseStorageService {

    private final RestClient restClient;
    private final String supabaseUrl;
    private final String secretKey;
    private final String bucket;

    public SupabaseStorageService (
            @Value("${supabase.url}") String supabaseUrl,
            @Value("${supabase.secret-key}") String secretKey,
            @Value("${supabase.bucket}") String bucket
    ) {
        this.restClient = RestClient.create();
        this.supabaseUrl = supabaseUrl;
        this.secretKey = secretKey;
        this.bucket = bucket;
    }

    // 이미지 업로드 → 누구나 볼 수 있는 이미지 주소(URL) 반환
    public String upload(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("이미지 파일만 업로드할 수 있습니다.");
        }

        // 저장 경로: posts/무작위이름.확장자
        String path = "posts/" + UUID.randomUUID() + getExtension(file.getOriginalFilename());

        try {
            restClient.post()
                    .uri(URI.create(supabaseUrl + "/storage/v1/object/" + bucket + "/" + path))
                    .header("apikey", secretKey)
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(file.getBytes())
                    .retrieve()
                    .toBodilessEntity();
        } catch (IOException | RestClientException e) {
            throw new IllegalArgumentException("이미지 저장에 실패했습니다.");
        }

        return supabaseUrl + "/storage/v1/object/public/" + bucket + "/" + path;
    }

    // 이미지 삭제 (업로드 때 받은 공개 URL을 그대로 넘기면 됨)
    public void delete(String imageUrl) {
        String prefix = supabaseUrl + "/storage/v1/object/public/" + bucket + "/";

        // 이 저장소 주소가 아니면(예: 예전 로컬 /uploads 이미지) 아무것도 안 함
        if (imageUrl == null || !imageUrl.startsWith(prefix)) {
            return;
        }

        String path = imageUrl.substring(prefix.length());

        try {
            restClient.delete()
                    .uri(URI.create(supabaseUrl + "/storage/v1/object/" + bucket + "/" + path))
                    .header("apikey", secretKey)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientException e) {
            // 파일 삭제에 실패해도 게시글 이미지 삭제(DB)는 진행되도록 넘어감
        }
    }

    // 파일 이름에서 확장자 꺼내기 (없으면 빈 문자열)
    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".")).toLowerCase();
    }

}
